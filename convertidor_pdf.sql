PGDMP                      }            convertidor_pdf    17.4    17.4                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false                       1262    16387    convertidor_pdf    DATABASE     u   CREATE DATABASE convertidor_pdf WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'es-MX';
    DROP DATABASE convertidor_pdf;
                     postgres    false            �            1259    16388    archivo    TABLE     �   CREATE TABLE public.archivo (
    nodo bigint,
    nombre "char" NOT NULL,
    peso bigint,
    id bigint NOT NULL,
    fecha date NOT NULL
);
    DROP TABLE public.archivo;
       public         heap r       postgres    false            �            1259    16391    usuario_id_seq    SEQUENCE     �   ALTER TABLE public.archivo ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    217                      0    16388    archivo 
   TABLE DATA           @   COPY public.archivo (nodo, nombre, peso, id, fecha) FROM stdin;
    public               postgres    false    217   �                  0    0    usuario_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.usuario_id_seq', 1, false);
          public               postgres    false    218                  x������ � �     