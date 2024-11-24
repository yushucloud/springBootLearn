CREATE TABLE public.jiuzhi_news (
	news_id int8 NOT NULL,
	title varchar NULL,
	description varchar NULL,
	cover varchar NULL,
	watches_count int4 NULL,
	"source" varchar NULL,
	pre_id int8 NULL,
	next_id int8 NULL,
	text_content varchar NULL,
	html_content varchar NULL,
	text_content_exclude_page varchar NULL,
	html_content_exclude_page varchar NULL,
	origin_content varchar NULL,
	origin_html_content varchar NULL,
	attachment varchar NULL,
	published_time timestamp NULL DEFAULT now(),
	createdtime timestamp NULL,
	CONSTRAINT jiuzhi_news_pk PRIMARY KEY (news_id)
);