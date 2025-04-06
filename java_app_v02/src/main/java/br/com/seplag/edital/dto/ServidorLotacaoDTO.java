package br.com.seplag.edital.dto; // Coloque no pacote apropriado (ex: dto)

// Pode usar Lombok @Data, @Getter, @Setter, @AllArgsConstructor, @NoArgsConstructor
public class ServidorLotacaoDTO {
    private String nome;
    private Integer idade;
    private String unidadeLotacaoNome;
    private String fotografiaUrl;

    // Construtor para facilitar a criação no Service
    public ServidorLotacaoDTO(String nome, Integer idade, String unidadeLotacaoNome, String fotografiaUrl) {
        this.nome = nome;
        this.idade = idade;
        this.unidadeLotacaoNome = unidadeLotacaoNome;
        this.fotografiaUrl = fotografiaUrl;
    }

    public String getNome() {
        return nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public String getUnidadeLotacaoNome() {
        return unidadeLotacaoNome;
    }

    public String getFotografiaUrl() {
        return fotografiaUrl;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public void setUnidadeLotacaoNome(String unidadeLotacaoNome) {
        this.unidadeLotacaoNome = unidadeLotacaoNome;
    }

    public void setFotografiaUrl(String fotografiaUrl) {
        this.fotografiaUrl = fotografiaUrl;
    }
}