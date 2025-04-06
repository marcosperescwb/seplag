package br.com.seplag.edital.service;

import br.com.seplag.edital.dto.ServidorLotacaoDTO; // <-- Importar DTO
import br.com.seplag.edital.dto.PessoaLotadaInfoDTO; // Import DTO intermediário

import br.com.seplag.edital.model.Lotacao; // Já importado
import br.com.seplag.edital.repository.LotacaoRepository;
import br.com.seplag.edital.repository.FotoPessoaRepository;
import br.com.seplag.edital.repository.UnidadeRepository; // Precisa ser injetado

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl; // Para criar nossa própria Page
import org.springframework.data.domain.Pageable;

@Service
public class LotacaoServiceImpl implements LotacaoService {

    private static final Logger logger = LoggerFactory.getLogger(LotacaoServiceImpl.class);

    @Autowired
    private LotacaoRepository lotacaoRepository;

    @Override
    public Page<Lotacao> listarLotacoes(Pageable pageable) {
        return lotacaoRepository.findAll(pageable);
    }

    @Override
    public Lotacao obterLotacaoPorId(Integer id) {
        Optional<Lotacao> lotacaoOptional = lotacaoRepository.findById(id);
        return lotacaoOptional.orElse(null);
    }

    @Autowired
    private FotoPessoaRepository fotoPessoaRepository;

    @Autowired
    private UnidadeRepository unidadeRepository; // Injetar para buscar nome da unidade

    @Autowired
    private MinioService minioService;

    @Override
    public Lotacao criarLotacao(Lotacao lotacao) {
        return lotacaoRepository.save(lotacao);
    }

    @Override
    public Lotacao atualizarLotacao(Integer id, Lotacao lotacao) {
        Optional<Lotacao> lotacaoOptional = lotacaoRepository.findById(id);
        if (lotacaoOptional.isPresent()) {
            lotacao.setLotId(id);
            return lotacaoRepository.save(lotacao);
        } else {
            return null;
        }
    }

    @Override
    public boolean deletarLotacao(Integer id) {
        if (lotacaoRepository.existsById(id)) {
            lotacaoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServidorLotacaoDTO> findServidoresEfetivosPorUnidade(Integer unidadeId, Pageable pageable) { // <-- Aceita Pageable, retorna Page
        if (unidadeId == null) {
            logger.warn("ID da unidade fornecido é nulo.");
            return Page.empty(pageable); // Retorna página vazia
        }

        logger.info("Buscando servidores efetivos lotados na unidade ID: {} (Página: {}, Tamanho: {}, Ordenação: {})",
                unidadeId, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        // 1. Busca a PÁGINA de DTOs intermediários do repositório
        Page<PessoaLotadaInfoDTO> paginaInfos = lotacaoRepository.findServidoresEfetivosLotadosInfo(unidadeId, pageable);
        logger.debug("Encontradas {} pessoas na página {} (Total: {}) para unidade {}.",
                paginaInfos.getNumberOfElements(), pageable.getPageNumber(), paginaInfos.getTotalElements(), unidadeId);

        if (!paginaInfos.hasContent()) {
            return Page.empty(pageable); // Retorna página vazia se não houver conteúdo
        }

        // 2. Mapeia o CONTEÚDO da página de DTOs intermediários para DTOs finais (com foto)
        List<ServidorLotacaoDTO> dtosFinais = new ArrayList<>();
        for (PessoaLotadaInfoDTO info : paginaInfos.getContent()) { // Itera sobre o conteúdo da página
            Integer idade = calcularIdade(info.getPessoaDataNascimento());
            String fotoUrl = null;

            Optional<String> fotoHashOpt = fotoPessoaRepository.findLatestFotoHashByPessoaId(info.getPessoaId());
            if (fotoHashOpt.isPresent()) {
                String fotoHash = fotoHashOpt.get();
                try {
                    fotoUrl = minioService.getPresignedUrl(fotoHash);
                } catch (Exception e) {
                    logger.error("Não foi possível gerar URL para a foto da pessoa ID {} (objeto MinIO '{}'): {}",
                            info.getPessoaId(), fotoHash, e.getMessage());
                }
            } else {
                logger.debug("Nenhuma foto encontrada para a pessoa ID {}", info.getPessoaId());
            }

            dtosFinais.add(new ServidorLotacaoDTO(
                    info.getPessoaNome(),
                    idade,
                    info.getUnidadeNome(), // Nome da unidade já veio no DTO intermediário
                    fotoUrl
            ));
        }

        logger.info("Retornando página com {} DTOs finais de servidores para unidade ID: {}", dtosFinais.size(), unidadeId);

        // 3. Cria e retorna um novo objeto Page com o conteúdo mapeado e as informações de paginação originais
        return new PageImpl<>(dtosFinais, pageable, paginaInfos.getTotalElements());
    }

    // Método auxiliar para calcular idade
    private Integer calcularIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            return null;
        }
        try {
            return Period.between(dataNascimento, LocalDate.now()).getYears();
        } catch (Exception e) {
            logger.error("Erro ao calcular idade para data {}: {}", dataNascimento, e.getMessage());
            return null;
        }
    }
}