-- CREATE USER service_user WITH PASSWORD 'service_pass';

CREATE DATABASE "curso-jsp";
\c "curso-jsp";

-- Table: public.usuario_cs

-- DROP TABLE IF EXISTS public.usuario_cs;

CREATE SEQUENCE IF NOT EXISTS id_logon_seq;

CREATE TABLE IF NOT EXISTS public.usuario_cs
(
    id_logon        bigint                                              NOT NULL DEFAULT nextval('id_logon_seq'::regclass),
    cod_funcionario bigint,
    logon           character varying(250) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_usuario_cs PRIMARY KEY (id_logon)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.usuario_cs
    OWNER to postgres;

-- Table: public.equipamento

-- DROP TABLE IF EXISTS public.equipamento;

CREATE SEQUENCE IF NOT EXISTS id_equipamento_seq;

CREATE TABLE IF NOT EXISTS public.equipamento
(
    id                      bigint                                              NOT NULL DEFAULT nextval('id_equipamento_seq'::regclass),
    descricao               character varying(250) COLLATE pg_catalog."default" NOT NULL,
    tipo_equipamento        bigint,
    data_nf                 date,
    numero_nf               bigint,
    cod_objetocusto         bigint,
    solicitante_equipamento character varying(250) COLLATE pg_catalog."default",
    usuario_cadastro        bigint,
    data_cadastro           date,
    CONSTRAINT pk_id_equipamento PRIMARY KEY (id)
)
    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.equipamento
    OWNER to postgres;

-- Table: public.equipamento

-- DROP TABLE IF EXISTS public.equipamento;

CREATE SEQUENCE IF NOT EXISTS id_login_seq;

CREATE TABLE IF NOT EXISTS public.model_login
(
    id    bigint                                              NOT NULL DEFAULT nextval('id_login_seq'::regclass),
    login character varying(200) COLLATE pg_catalog."default" NOT NULL,
    senha character varying(200) COLLATE pg_catalog."default" NOT NULL,
    nome  character varying(300) COLLATE pg_catalog."default" NOT NULL,
    email character varying(300) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT model_login_pkey PRIMARY KEY (id),
    CONSTRAINT login_unique UNIQUE (login)
);

ALTER TABLE IF EXISTS public.model_login
    OWNER to postgres;

INSERT INTO model_login(login, senha, nome, email)
VALUES ('admin', 'admin', 'Administrator', 'admin@admin.com');

-- String sql = "select * from model_login where upper(login) = upper(?) and upper(senha) = upper(?) ";
