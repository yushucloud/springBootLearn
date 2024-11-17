CREATE TABLE public.tm_brand (
	id bigserial NOT NULL,
	class_id int4 NOT NULL,
	class_name varchar(500) NULL,
	first_describ varchar(1000) NULL,
	first_notes varchar(5000) NULL,
	level1_id int4 NULL,
	level2_id int4 NULL,
	level_code int4 NULL,
	price int4 NULL,
	version_code varchar(50) NULL,
	create_time timestamp NULL,
	create_user varchar(50) NULL,
	update_time timestamp NULL,
	update_user varchar(50) NULL,
	CONSTRAINT tm_brand_pk PRIMARY KEY (id)
);
COMMENT ON TABLE public.tm_brand IS '商标分类表(尼斯表)';

-- Column comments

COMMENT ON COLUMN public.tm_brand.class_id IS '分类id';
COMMENT ON COLUMN public.tm_brand.class_name IS '分类名称';
COMMENT ON COLUMN public.tm_brand.first_describ IS '一级分类描述';
COMMENT ON COLUMN public.tm_brand.first_notes IS '一级分类注释';
COMMENT ON COLUMN public.tm_brand.level1_id IS '父级：一级分类id';
COMMENT ON COLUMN public.tm_brand.level2_id IS '父级：二级分类id';
COMMENT ON COLUMN public.tm_brand.level_code IS '层级';
COMMENT ON COLUMN public.tm_brand.price IS '价格';
COMMENT ON COLUMN public.tm_brand.version_code IS '版本';