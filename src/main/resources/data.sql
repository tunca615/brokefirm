INSERT INTO customers (id, username, password, role) VALUES
(1, 'admin', '$2a$12$fB1HgPTOrl2lic2OFPbMPeNCwCsTIwW/h5BAr3.76PdJs22MiRxdy', 'ROLE_ADMIN');
INSERT INTO customers (id, username, password, role) VALUES
(2, 'user', '$2a$12$GMUHhYeudQgN/LAzwKyfKecwYj0as228.JiwJGjt6AVWCd14JLc/6', 'ROLE_USER');
INSERT INTO assets (customer_id, asset_name, size, usable_size) VALUES (2, 'TRY', 10000, 10000);
