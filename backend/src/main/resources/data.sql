INSERT INTO TB_CATEGORIA (nome, descricao) VALUES ('Tecnologia', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.');
INSERT INTO TB_CATEGORIA (nome, descricao) VALUES ('Educação Financeira', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.');
INSERT INTO TB_CATEGORIA (nome, descricao) VALUES ('Desenvolvimento Pessoal', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.');

INSERT INTO TB_AUTOR (nome, email, DATA_NASCIMENTO) VALUES ('Pedro Ferreira', 'pedro@gmail.com', '1983-07-13');
INSERT INTO TB_AUTOR (nome, email, DATA_NASCIMENTO) VALUES ('João da Silva', 'joao@gmail.com', '1979-01-20');
INSERT INTO TB_AUTOR (nome, email, DATA_NASCIMENTO) VALUES ('Maria Aparecida', 'maria@gmail.com', '1980-05-31');
INSERT INTO TB_AUTOR (nome, email, DATA_NASCIMENTO) VALUES ('Jorge Lucas', 'jorge@gmail.com', '1985-05-31');


INSERT INTO TB_LIVRO (titulo, isbn, ANO_PUBLICACAO, preco, AUTOR_ID, CATEGORIA_ID) VALUES ('Java', '1552369871', 2021, 150.50, 2, 1);
INSERT INTO TB_LIVRO (titulo, isbn, ANO_PUBLICACAO, preco, AUTOR_ID, CATEGORIA_ID) VALUES ('Python', '2552369872', 2023, 99.0, 2, 1);
INSERT INTO TB_LIVRO (titulo, isbn, ANO_PUBLICACAO, preco, AUTOR_ID, CATEGORIA_ID) VALUES ('JavaScript','3552369873', 2020, 90.0, 1, 1);
INSERT INTO TB_LIVRO (titulo, isbn, ANO_PUBLICACAO, preco, AUTOR_ID, CATEGORIA_ID) VALUES ('Banco de Dados', '4552369874', 2019, 100.90, 1, 1);
INSERT INTO TB_LIVRO (titulo, isbn, ANO_PUBLICACAO, preco, AUTOR_ID, CATEGORIA_ID) VALUES ('HTML 5', '5552369875', 2017, 105.0, 2, 1);
INSERT INTO TB_LIVRO (titulo, isbn, ANO_PUBLICACAO, preco, AUTOR_ID, CATEGORIA_ID) VALUES ('Pai Rico Pai Pobre', '6552369876', 1999, 65.0, 3, 2);
INSERT INTO TB_LIVRO (titulo, isbn, ANO_PUBLICACAO, preco, AUTOR_ID, CATEGORIA_ID) VALUES ('MINDSET', '7552369877', 2015, 80.0, 4, 3);