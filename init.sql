--
-- PostgreSQL database dump
--

-- Dumped from database version 13.20 (Debian 13.20-1.pgdg120+1)
-- Dumped by pg_dump version 13.20

-- Started on 2025-03-31 11:53:33 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 3090 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 205 (class 1259 OID 16636)
-- Name: cidade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cidade (
    cid_id bigint NOT NULL,
    cid_nome character varying(255),
    cid_uf character varying(255)
);


ALTER TABLE public.cidade OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 16634)
-- Name: cidade_cid_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cidade_cid_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cidade_cid_id_seq OWNER TO postgres;

--
-- TOC entry 3091 (class 0 OID 0)
-- Dependencies: 204
-- Name: cidade_cid_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cidade_cid_id_seq OWNED BY public.cidade.cid_id;


--
-- TOC entry 214 (class 1259 OID 16699)
-- Name: endereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.endereco (
    end_id integer NOT NULL,
    end_tipo_logradouro character varying(255),
    end_logradouro character varying(255),
    end_numero integer,
    end_bairro character varying(255),
    cid_id bigint
);


ALTER TABLE public.endereco OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 16697)
-- Name: endereco_end_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.endereco_end_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.endereco_end_id_seq OWNER TO postgres;

--
-- TOC entry 3092 (class 0 OID 0)
-- Dependencies: 213
-- Name: endereco_end_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.endereco_end_id_seq OWNED BY public.endereco.end_id;


--
-- TOC entry 207 (class 1259 OID 16644)
-- Name: foto_pessoa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.foto_pessoa (
    fp_id integer NOT NULL,
    pes_id integer,
    fp_data date,
    fp_bucket character varying(255),
    fp_hash character varying(255)
);


ALTER TABLE public.foto_pessoa OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16642)
-- Name: foto_pessoa_fp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.foto_pessoa_fp_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.foto_pessoa_fp_id_seq OWNER TO postgres;

--
-- TOC entry 3093 (class 0 OID 0)
-- Dependencies: 206
-- Name: foto_pessoa_fp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.foto_pessoa_fp_id_seq OWNED BY public.foto_pessoa.fp_id;


--
-- TOC entry 211 (class 1259 OID 16673)
-- Name: lotacao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lotacao (
    lot_id integer NOT NULL,
    pes_id integer,
    unid_id integer,
    lot_data_lotacao date,
    lot_data_remocao date,
    lot_portaria character varying(255)
);


ALTER TABLE public.lotacao OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16671)
-- Name: lotacao_lot_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.lotacao_lot_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.lotacao_lot_id_seq OWNER TO postgres;

--
-- TOC entry 3094 (class 0 OID 0)
-- Dependencies: 210
-- Name: lotacao_lot_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.lotacao_lot_id_seq OWNED BY public.lotacao.lot_id;


--
-- TOC entry 201 (class 1259 OID 16616)
-- Name: pessoa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoa (
    pes_id integer NOT NULL,
    pes_nome character varying(255),
    pes_data_nascimento date,
    pes_sexo character varying(255),
    pes_mae character varying(255),
    pes_pai character varying(255)
);


ALTER TABLE public.pessoa OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16718)
-- Name: pessoa_endereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pessoa_endereco (
    pes_id integer,
    end_id integer
);


ALTER TABLE public.pessoa_endereco OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16614)
-- Name: pessoa_pes_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pessoa_pes_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pessoa_pes_id_seq OWNER TO postgres;

--
-- TOC entry 3095 (class 0 OID 0)
-- Dependencies: 200
-- Name: pessoa_pes_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pessoa_pes_id_seq OWNED BY public.pessoa.pes_id;


--
-- TOC entry 209 (class 1259 OID 16663)
-- Name: servidor_efetivo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.servidor_efetivo (
    pes_id integer,
    se_matricula character varying(255)
);


ALTER TABLE public.servidor_efetivo OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16655)
-- Name: servidor_temporario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.servidor_temporario (
    pes_id integer,
    st_data_admissao date,
    st_data_demissao date
);


ALTER TABLE public.servidor_temporario OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16628)
-- Name: unidade; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unidade (
    unid_id integer NOT NULL,
    unid_nome character varying(255),
    unid_sigla character varying(255)
);


ALTER TABLE public.unidade OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16689)
-- Name: unidade_endereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.unidade_endereco (
    unid_id integer,
    end_id integer
);


ALTER TABLE public.unidade_endereco OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16626)
-- Name: unidade_unid_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.unidade_unid_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.unidade_unid_id_seq OWNER TO postgres;

--
-- TOC entry 3096 (class 0 OID 0)
-- Dependencies: 202
-- Name: unidade_unid_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.unidade_unid_id_seq OWNED BY public.unidade.unid_id;


--
-- TOC entry 2929 (class 2604 OID 16731)
-- Name: cidade cid_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cidade ALTER COLUMN cid_id SET DEFAULT nextval('public.cidade_cid_id_seq'::regclass);


--
-- TOC entry 2932 (class 2604 OID 16702)
-- Name: endereco end_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.endereco ALTER COLUMN end_id SET DEFAULT nextval('public.endereco_end_id_seq'::regclass);


--
-- TOC entry 2930 (class 2604 OID 16647)
-- Name: foto_pessoa fp_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.foto_pessoa ALTER COLUMN fp_id SET DEFAULT nextval('public.foto_pessoa_fp_id_seq'::regclass);


--
-- TOC entry 2931 (class 2604 OID 16676)
-- Name: lotacao lot_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lotacao ALTER COLUMN lot_id SET DEFAULT nextval('public.lotacao_lot_id_seq'::regclass);


--
-- TOC entry 2927 (class 2604 OID 16619)
-- Name: pessoa pes_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa ALTER COLUMN pes_id SET DEFAULT nextval('public.pessoa_pes_id_seq'::regclass);


--
-- TOC entry 2928 (class 2604 OID 16631)
-- Name: unidade unid_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidade ALTER COLUMN unid_id SET DEFAULT nextval('public.unidade_unid_id_seq'::regclass);


--
-- TOC entry 2938 (class 2606 OID 16733)
-- Name: cidade cidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cidade
    ADD CONSTRAINT cidade_pkey PRIMARY KEY (cid_id);


--
-- TOC entry 2944 (class 2606 OID 16704)
-- Name: endereco endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (end_id);


--
-- TOC entry 2940 (class 2606 OID 16649)
-- Name: foto_pessoa foto_pessoa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.foto_pessoa
    ADD CONSTRAINT foto_pessoa_pkey PRIMARY KEY (fp_id);


--
-- TOC entry 2942 (class 2606 OID 16678)
-- Name: lotacao lotacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lotacao
    ADD CONSTRAINT lotacao_pkey PRIMARY KEY (lot_id);


--
-- TOC entry 2934 (class 2606 OID 16624)
-- Name: pessoa pessoa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa
    ADD CONSTRAINT pessoa_pkey PRIMARY KEY (pes_id);


--
-- TOC entry 2936 (class 2606 OID 16633)
-- Name: unidade unidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidade
    ADD CONSTRAINT unidade_pkey PRIMARY KEY (unid_id);


--
-- TOC entry 2952 (class 2606 OID 16753)
-- Name: endereco endereco_cid_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.endereco
    ADD CONSTRAINT endereco_cid_id_fkey FOREIGN KEY (cid_id) REFERENCES public.cidade(cid_id);


--
-- TOC entry 2945 (class 2606 OID 16650)
-- Name: foto_pessoa foto_pessoa_pes_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.foto_pessoa
    ADD CONSTRAINT foto_pessoa_pes_id_fkey FOREIGN KEY (pes_id) REFERENCES public.pessoa(pes_id);


--
-- TOC entry 2948 (class 2606 OID 16679)
-- Name: lotacao lotacao_pes_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lotacao
    ADD CONSTRAINT lotacao_pes_id_fkey FOREIGN KEY (pes_id) REFERENCES public.pessoa(pes_id);


--
-- TOC entry 2949 (class 2606 OID 16684)
-- Name: lotacao lotacao_unid_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lotacao
    ADD CONSTRAINT lotacao_unid_id_fkey FOREIGN KEY (unid_id) REFERENCES public.unidade(unid_id) NOT VALID;


--
-- TOC entry 2954 (class 2606 OID 16726)
-- Name: pessoa_endereco pessoa_endereco_end_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_endereco
    ADD CONSTRAINT pessoa_endereco_end_id_fkey FOREIGN KEY (end_id) REFERENCES public.endereco(end_id) NOT VALID;


--
-- TOC entry 2953 (class 2606 OID 16721)
-- Name: pessoa_endereco pessoa_endereco_pes_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pessoa_endereco
    ADD CONSTRAINT pessoa_endereco_pes_id_fkey FOREIGN KEY (pes_id) REFERENCES public.pessoa(pes_id);


--
-- TOC entry 2947 (class 2606 OID 16666)
-- Name: servidor_efetivo servidor_efetivo_pes_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servidor_efetivo
    ADD CONSTRAINT servidor_efetivo_pes_id_fkey FOREIGN KEY (pes_id) REFERENCES public.pessoa(pes_id);


--
-- TOC entry 2946 (class 2606 OID 16658)
-- Name: servidor_temporario servidor_temporario_pes_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servidor_temporario
    ADD CONSTRAINT servidor_temporario_pes_id_fkey FOREIGN KEY (pes_id) REFERENCES public.pessoa(pes_id);


--
-- TOC entry 2951 (class 2606 OID 16710)
-- Name: unidade_endereco unidade_endereco_end_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidade_endereco
    ADD CONSTRAINT unidade_endereco_end_id_fkey FOREIGN KEY (end_id) REFERENCES public.endereco(end_id) NOT VALID;


--
-- TOC entry 2950 (class 2606 OID 16692)
-- Name: unidade_endereco unidade_endereco_unid_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.unidade_endereco
    ADD CONSTRAINT unidade_endereco_unid_id_fkey FOREIGN KEY (unid_id) REFERENCES public.unidade(unid_id);


-- Completed on 2025-03-31 11:53:33 UTC

--
-- PostgreSQL database dump complete
--

