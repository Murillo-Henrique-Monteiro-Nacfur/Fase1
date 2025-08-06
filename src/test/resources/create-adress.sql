INSERT INTO public."USERS" ("ID", "NAME", "EMAIL", "LOGIN", "PASSWORD", "BIRTH_DATE", "ROLE") VALUES (1001, 'Usuário Teste', 'teste@teste.com', 'testuser', '$2a$10$NF9KbDay8L1a0KuEP5Dvd.V3nAFHOuXQjE7iliCKvclcPVhmE55ym', '1990-01-01', 'ADMIN');

INSERT INTO public."ADDRESS" ("ID", "STREET", "NUMBER", "CITY", "STATE", "COUNTRY", "NEIGHBORHOOD", "ZIP_CODE", "ADDRESSABLE_ID", "ADDRESSABLE_TYPE") VALUES (2001, 'Rua dos Testes', '123', 'Cidade Teste', 'SP', 'Brasil', 'Bairro Teste', '12345-678', 1001, 'User');

INSERT INTO public."RESTAURANT" ("ID", "NAME", "CNPJ", "DESCRIPTION", "CUISINE_TYPE", "OPEN_TIME", "CLOSE_TIME", "USER_ID", "CREATED_AT", "UPDATED_AT") VALUES (3001, 'Restaurante Teste', '12345678000199', 'Restaurante para testes', 'Italiana', '10:00', '22:00', 1001, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
