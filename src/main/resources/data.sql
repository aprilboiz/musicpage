--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3 (Debian 16.3-1.pgdg120+1)
-- Dumped by pg_dump version 16.3


--
-- Data for Name: emotion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.emotion (emotion_id, emotion_name) VALUES (1, 'Happy');
INSERT INTO public.emotion (emotion_id, emotion_name) VALUES (2, 'Sad');
INSERT INTO public.emotion (emotion_id, emotion_name) VALUES (3, 'Angry');
INSERT INTO public.emotion (emotion_id, emotion_name) VALUES (4, 'Excited');
INSERT INTO public.emotion (emotion_id, emotion_name) VALUES (5, 'Calm');
INSERT INTO public.emotion (emotion_id, emotion_name) VALUES (6, 'Tired');


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.person (person_id, address, email, name, phone_number) VALUES (1, '1234 Wonderland', 'user@gmail.com', 'Alice', '1234567890');


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles (role_id, role_name) VALUES (1, 'USER');
INSERT INTO public.roles (role_id, role_name) VALUES (2, 'ADMIN');


--
-- Data for Name: songs; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.songs (id, author, title, youtube_id) VALUES (1, 'Andiez Official', '1 Phút - Andiez', 'dLQe4qEfVJw');
INSERT INTO public.songs (id, author, title, youtube_id) VALUES (2, 'Trúc Nhân', 'LỚN RỒI CÒN KHÓC NHÈ  ( MV )  TRÚC NHÂN (#LRCKN)', 'pFSQh_5QE40');
INSERT INTO public.songs (id, author, title, youtube_id) VALUES (3, 'Hòa Minzy', 'Bật Tình Yêu Lên - Hòa Minzy x Tăng Duy Tân  MV Lyrics', 'VHjMJeLsI0o');
INSERT INTO public.songs (id, author, title, youtube_id) VALUES (4, 'Anh Tú Atus', 'Bài này không để đi diễn  Anh Tu Atus x @DieuNhiOfficial Wedding', 'i0yxWxk_e20');
INSERT INTO public.songs (id, author, title, youtube_id) VALUES (5, 'HIEUTHUHAI', 'HIEUTHUHAI - Không Thể Say  Live Performance at Sóng 24', '5PvzGDU8WjM');
INSERT INTO public.songs (id, author, title, youtube_id) VALUES (6, 'Andiez Official', 'SUÝT NỮA THÌ  OFFICIAL OST  CHUYẾN ĐI CỦA THANH XUÂN  ANDIEZ x BITI''S HUNTER  LYRIC VIDEO', 'cUmpJ2zwfVU');
INSERT INTO public.songs (id, author, title, youtube_id) VALUES (7, 'Christina Perri', 'christina perri - jar of hearts [official music video]', '8v_4O44sfjM');
INSERT INTO public.songs (id, author, title, youtube_id) VALUES (8, 'ACV Entertainment', 'TỪNG YÊU - PHAN DUY ANH  [OFFICIAL MUSIC VIDEO]', 'YRFSTg9IXNc');
INSERT INTO public.songs (id, author, title, youtube_id) VALUES (9, 'HIANHTRAI  Official', 'CÔ PHÒNG - HỒ QUANG HIẾU x HUỲNH VĂN  (Cover) x HỒ THANH TĂNG  (hianhtrai)', 'kmPsTtrBTCU');
INSERT INTO public.songs (id, author, title, youtube_id) VALUES (10, 'Sơn Tùng M-TP Official', 'SƠN TÙNG M-TP  ĐỪNG LÀM TRÁI TIM ANH ĐAU  OFFICIAL MUSIC VIDEO', 'abPmZCZZrFA');


--
-- Data for Name: song_emotion; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.song_emotion (emotion_id, song_id) VALUES (1, 1);
INSERT INTO public.song_emotion (emotion_id, song_id) VALUES (1, 2);
INSERT INTO public.song_emotion (emotion_id, song_id) VALUES (1, 3);
INSERT INTO public.song_emotion (emotion_id, song_id) VALUES (1, 4);
INSERT INTO public.song_emotion (emotion_id, song_id) VALUES (1, 5);
INSERT INTO public.song_emotion (emotion_id, song_id) VALUES (2, 6);
INSERT INTO public.song_emotion (emotion_id, song_id) VALUES (2, 7);
INSERT INTO public.song_emotion (emotion_id, song_id) VALUES (2, 8);
INSERT INTO public.song_emotion (emotion_id, song_id) VALUES (2, 9);
INSERT INTO public.song_emotion (emotion_id, song_id) VALUES (2, 10);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (person_id, user_id, password, username) VALUES (NULL, 1, '$2a$10$dVMHyyOyoCdMClF71LFaUesBvMZspWgzfcH3ry/zCEsjVQ/XxQHWO', 'user');
INSERT INTO public.users (person_id, user_id, password, username) VALUES (1, 2, '$2a$10$pIDcKG50jhMFNreKLKpNGORghJchA9UjpLjNsXv3WLINkjLt.WCWK', 'user@gmail.com');
INSERT INTO public.users (person_id, user_id, password, username) VALUES (NULL, 3, '$2a$10$lh4mHWxH94cPaX9l2drtyuOeEmb7xRiFAwabxK1Y6bB.vmKvxC.xG', 'admin');
INSERT INTO public.users (person_id, user_id, password, username) VALUES (NULL, 4, '$2a$10$uLVcoS.MeWuupnS.GItiwe9IyNM9nPewIwtB2PUOgIgVtnYAg6Ha.', 'superuser');


--
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.user_role (role_id, user_id) VALUES (1, 1);
INSERT INTO public.user_role (role_id, user_id) VALUES (1, 2);
INSERT INTO public.user_role (role_id, user_id) VALUES (2, 3);
INSERT INTO public.user_role (role_id, user_id) VALUES (1, 4);
INSERT INTO public.user_role (role_id, user_id) VALUES (2, 4);


--
-- Name: emotion_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.emotion_seq', 51, true);


--
-- Name: person_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.person_seq', 1, true);


--
-- Name: roles_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.roles_seq', 51, true);


--
-- Name: songs_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.songs_seq', 51, true);


--
-- Name: users_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_seq', 51, true);


--
-- PostgreSQL database dump complete
--

