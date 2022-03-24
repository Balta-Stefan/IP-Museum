INSERT INTO Clients(address, phone, country, city, email, availableFunds, enabled) VALUES('Muzeja 1', '051-322-111', 'BiH', 'Sarajevo', 'muzej1@mail.com', 0, 1);
INSERT INTO Clients(address, phone, country, city, email, availableFunds, enabled) VALUES('Potrosaca 1', '111-231-333', 'BiH', 'Tuzla', 'kupac1@mail.com', 512, 1);

INSERT INTO Persons(id, firstName, lastName, cardNumber, cardType, cardExpirationDate, pin) VALUES(2, 'Marko', 'Markovic', '2906374445736006', 'AmericanExpress', '2024-02-15', '6815');

INSERT INTO Companies(id, name, token) VALUES(1, 'Prvi muzej', '$2a$10$/r9i2Br30m65YUQ5VA16nurs79sC5ptzY0vj/UV/gZfLsS1hphEBa');