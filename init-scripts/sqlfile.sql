--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2 (Debian 17.2-1.pgdg120+1)
-- Dumped by pg_dump version 17.1

-- Started on 2025-03-01 14:58:33

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 24616)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 3392 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 32849)
-- Name: reservations; Type: TABLE; Schema: public; Owner: usersystem
--

CREATE TABLE public.reservations (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    user_id uuid,
    table_number integer NOT NULL,
    people integer NOT NULL,
    surname character varying(255),
    name character varying(255),
    phone character varying(255),
    email character varying(255) NOT NULL,
    notes text,
    date date NOT NULL,
    "time" character varying(255) NOT NULL,
    iscompleted boolean
);


ALTER TABLE public.reservations OWNER TO usersystem;

--
-- TOC entry 221 (class 1259 OID 32858)
-- Name: tables; Type: TABLE; Schema: public; Owner: usersystem
--

CREATE TABLE public.tables (
    id integer NOT NULL,
    people integer
);


ALTER TABLE public.tables OWNER TO usersystem;

--
-- TOC entry 220 (class 1259 OID 32857)
-- Name: tables_id_seq; Type: SEQUENCE; Schema: public; Owner: usersystem
--

CREATE SEQUENCE public.tables_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tables_id_seq OWNER TO usersystem;

--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 220
-- Name: tables_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: usersystem
--

ALTER SEQUENCE public.tables_id_seq OWNED BY public.tables.id;


--
-- TOC entry 218 (class 1259 OID 24635)
-- Name: users; Type: TABLE; Schema: public; Owner: usersystem
--

CREATE TABLE public.users (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    username character varying(255),
    email character varying(255),
    password character varying(255),
    birthyear date,
    role character varying(255)
);


ALTER TABLE public.users OWNER TO usersystem;

--
-- TOC entry 3231 (class 2604 OID 32861)
-- Name: tables id; Type: DEFAULT; Schema: public; Owner: usersystem
--

ALTER TABLE ONLY public.tables ALTER COLUMN id SET DEFAULT nextval('public.tables_id_seq'::regclass);


--
-- TOC entry 3384 (class 0 OID 32849)
-- Dependencies: 219
-- Data for Name: reservations; Type: TABLE DATA; Schema: public; Owner: usersystem
--

COPY public.reservations (id, user_id, table_number, people, surname, name, phone, email, notes, date, "time", iscompleted) FROM stdin;
3ed6de7b-330c-482c-b8fc-e0ff964a2ef0	2f479b0b-0fb1-40ba-8bc1-53e43f1edd8b	12	6	testsurname	Χρήστος	6948823653	testemail@hotmail.com	trest	2025-02-12	02:30	t
d8d4aff1-9dd1-4448-9dcd-1d1bcbecfcae	\N	12	3	testsurname	Χρήστος	6947512341	testemail@hotmail.com		2025-02-25	22:45	\N
30453995-5571-4f3f-bafb-d42dd3ed6c9e	2f479b0b-0fb1-40ba-8bc1-53e43f1edd8b	12	5	Dokimastiko	Christos	6948823653	testemail@hotmail.com		2025-02-25	11:10	\N
32cab25b-29f4-41f8-b2b3-25ac26c52ed6	2f479b0b-0fb1-40ba-8bc1-53e43f1edd8b	4	6	Dokimastiko	Christos	6948823653	usertest@gmail.com	\N	2025-02-28	21:00	\N
58c2929d-b501-49ab-92bd-2018cdc9747d	2f479b0b-0fb1-40ba-8bc1-53e43f1edd8b	12	6	Dokimastiko	Christos	6948823653	testemail@hotmail.com		2025-02-28	02:30	\N
6406192b-1b73-458f-9b02-9468c6e68bbf	2f479b0b-0fb1-40ba-8bc1-53e43f1edd8b	4	6	Dokimastiko	Christos	6948823653	usertest@gmail.com	\N	2025-02-27	21:30	\N
9ca895d8-6541-44c5-a765-54b8d17540d3	2f479b0b-0fb1-40ba-8bc1-53e43f1edd8b	4	6	Dokimastiko	Christos	6948823653	usertest@gmail.com	\N	2025-02-26	22:00	\N
cf96ede7-c51e-45ad-b8a3-365569388d80	2f479b0b-0fb1-40ba-8bc1-53e43f1edd8b	12	6	Dokimastiko	Christos	6948823653	testemail@hotmail.com		2025-02-28	02:30	\N
d13e6499-752f-eedb-919f-76d9f3e5457c	2f479b0b-0fb1-40ba-8bc1-53e43f1edd8b	12	6	testsurname	Χρήστος	6947512341	testemail@hotmail.com		2025-02-16	02:30	f
1043177a-bc99-411b-9fc0-807d41301958	\N	12	3	testsurname	Χρήστος	6947512341	testemail@hotmail.com		2025-02-25	22:45	\N
eb9201d2-6014-444b-ab59-4e8639c55ae6	\N	12	3	testsurname	Christos	6948823653	usertest@gmail.com		2025-02-25	22:45	\N
dbc0ae7b-0b2e-4e62-9d23-01d39261afe9	8e1f6a09-abf5-4f7f-821f-7c436b94a519	12	3	Ioadnnidis	Χρήστοdς	6948823653	dfgdh@yahoo.gr		2025-02-26	12:14	\N
9905c670-9e0a-4206-bc12-d95a050ac592	4eb75b2a-c41d-4b6d-a461-1d1fcab99fae	12	3	Ioad4nnidis	Χρή4στοdς	6948823653	df4gdh@yahoo.gr	dfgdf	2025-02-26	12:14	\N
3c280590-a982-4e04-a1be-6b5792da8ad1	\N	12	3	Ioad4nnidis	Χρή4στοdς	6948823653	df4gdh@yahoo.gr	dfgdf	2025-02-26	12:14	\N
9b9bed61-7df6-4637-ab82-60ab7455aca2	\N	12	3	Ioad4nnidis	Χρή4στοdς	6948823653	df4gdh@yahoo.gr	dokimi xoris na eimai sundedemenos kai xwris na ftiaksv acc	2025-02-26	12:14	\N
fc30b654-3b30-489c-b34b-9930f4d3c4dd	2f479b0b-0fb1-40ba-8bc1-53e43f1edd8b	12	3	iwannidisx	iwannidisx	6948823653	usertest@gmail.com	meta hackathon	2025-03-07	21:04	\N
d13e6499-752f-4ddb-919f-76d9f3e5457c	2f479b0b-0fb1-40ba-8bc1-53e43f1edd8b	12	6	testsurname	Χρήστος	6947512341	testemail@hotmail.com		2025-02-28	21:30	\N
022f91d3-3656-4955-b386-6f3c617ae368	2f479b0b-0fb1-40ba-8bc1-53e43f1edd8b	12	6	Mavrikos	Χρήστος	6947512341	maura@gmail.com		2025-03-01	21:30	\N
020cae25-d72c-4916-8b91-fdf3bf6975e6	\N	12	4	Dokimastiko	Χρήστος	6947599499	usertest@gmail.com	META TO HAVCJATHON NIKI 2	2025-03-12	17:50	\N
10ea8f88-cc80-4fe3-954d-bd0534d15f36	\N	15	2	Dokimastiko	Χρήστος	6948823653	usertest@gmail.com	;dfjglkfjbgljHAVACKAKAJBJ2-203U9243768746875362923	2025-03-15	19:55	\N
\.


--
-- TOC entry 3386 (class 0 OID 32858)
-- Dependencies: 221
-- Data for Name: tables; Type: TABLE DATA; Schema: public; Owner: usersystem
--

COPY public.tables (id, people) FROM stdin;
1	6
2	6
3	6
4	6
5	6
6	5
7	4
8	4
9	4
10	4
11	3
13	2
14	2
15	2
12	6
\.


--
-- TOC entry 3383 (class 0 OID 24635)
-- Dependencies: 218
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: usersystem
--

COPY public.users (id, username, email, password, birthyear, role) FROM stdin;
62528664-43f0-46cd-8e04-dcbdf0a274d1	xristosio	testemail@hotmail.com	$2a$10$sSrLiBxw6qcm5yNEWkbKreWmacGRn1Nqy2OKL5/1VnZ.UcuFEcb/O	2001-10-15	MANAGER
2f479b0b-0fb1-40ba-8bc1-53e43f1edd8b	iwannidisx	usertest@gmail.com	$2a$10$rYAbFb.nZYzESOEuefZiP.sJGCQ9P3krHLPaUaYWnIgHbrQH0ga3G	2001-10-15	USER
090e1777-0735-4dd6-a71c-45591499d3b2	panosmauros	maura@gmail.com	$2a$10$2Ry/PX0lhA0TY.0JBx/GYeUVmO8AtIUO8I921yqIz7DI1gpp2zmdC	2001-04-01	USER
2cffc212-f004-47cc-98b4-c4f1b0d7417b	Χρήστος	dfgh@yahoo.gr	$2a$10$/P.ON7Z1QyaRQWsMkN.wOemCOhqiz7ZZmfxf163J0eV5/fXCQm7wi	2025-02-12	USER
8e1f6a09-abf5-4f7f-821f-7c436b94a519	Χρήστοdς	dfgdh@yahoo.gr	$2a$10$.//AQ1RHtqL.RfHurhAN7u2elfB.BdsVxliI8shsHPCV3lmN3WIYG	2025-02-12	USER
4eb75b2a-c41d-4b6d-a461-1d1fcab99fae	Χρή4στοdς	df4gdh@yahoo.gr	$2a$10$XEeer/cfmI/BmIhrkt/PkOwyyaW.5FJmGWIyzYt/zCPbm5RflbbSO	2025-02-12	USER
\.


--
-- TOC entry 3394 (class 0 OID 0)
-- Dependencies: 220
-- Name: tables_id_seq; Type: SEQUENCE SET; Schema: public; Owner: usersystem
--

SELECT pg_catalog.setval('public.tables_id_seq', 15, true);


--
-- TOC entry 3235 (class 2606 OID 32856)
-- Name: reservations reservations_pkey; Type: CONSTRAINT; Schema: public; Owner: usersystem
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT reservations_pkey PRIMARY KEY (id);


--
-- TOC entry 3237 (class 2606 OID 32863)
-- Name: tables tables_pkey; Type: CONSTRAINT; Schema: public; Owner: usersystem
--

ALTER TABLE ONLY public.tables
    ADD CONSTRAINT tables_pkey PRIMARY KEY (id);


--
-- TOC entry 3233 (class 2606 OID 24642)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: usersystem
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


-- Completed on 2025-03-01 14:58:33

--
-- PostgreSQL database dump complete
--

