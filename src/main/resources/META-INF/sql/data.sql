INSERT INTO additional_info_clients VALUES (null, "ul. Alberta 3/43, 01-577 Warszawa", "77249000056420077578477743", "kulczyk.j@gmail.com", 550, "9318235090", "879876543")
INSERT INTO additional_info_clients VALUES (null, "ul. Krecia 2, 01-343 Warszawa", "86106000058780488477816280", "tarcz.and@onet.pl", 480, "8943064667", "564432123")
INSERT INTO additional_info_clients VALUES (null, "ul. Zamszy 7, 01-434 Warszawa", "59150017356396376097274923", "info.polexport@polex.pl", 700, "1219027734", "512098876")

INSERT INTO clients_natural_person VALUES (1000, 1, "Janusz", "Kulczyk")
INSERT INTO clients_natural_person VALUES (1001, 2, "Andrzej", "Tarczynski")
INSERT INTO clients_legal_person VALUES (1002, 3, "PolExport sp. z o.o.")

INSERT INTO additional_info_employees VALUES (null, 500, 300, "546434654", 60000)
INSERT INTO additional_info_employees VALUES (null, 400, 100, "634567987", 20000)
INSERT INTO additional_info_employees VALUES (null, 300, 60, "765456987", 12000)
INSERT INTO additional_info_employees VALUES (null, 200, 40, "546768987", 9000)
INSERT INTO additional_info_employees VALUES (null, 600, 400, "654765876", 80000)

INSERT INTO employees VALUES (null, "adam.kowalski@PKlegal.pl", "Adam", 1, "Kowalski", "$2a$10$LusF4zmyfIfHTvA8KKSLEO.lrY8Y6LX4UHo99uqV0.sS2D5EJZAPi", 1)
INSERT INTO employees VALUES (null, "k.janicki@PKlegal.pl", "Krzysztof", 0, "Janicki", "$2a$10$fgAg6NLP7JIHjiyOl5.Z0uNArcKjrthwOFEYsqMkWU3K.DScXtZi6", 2)
INSERT INTO employees VALUES (null, "patryk.zubrzycki@PKlegal.pl", "Patryk", 0, "Zubrzycki", "$2a$10$3Zt7i.Zwjr0bahsObyu0vuVJENxQWEPYvqciZExZM4ki1NpCodyMe", 3)
INSERT INTO employees VALUES (null, "pawel.janiak@PKlegal.pl", "Pawel", 0, "Janiak", "$2a$10$4JUnbHe/sW1o8NOUYt9dRuzL8wQitlHZOjoIQtfwjZRK9nIHVxpr6", 4)
INSERT INTO employees VALUES (null, "zenon.krzywiski@PKlegal.pl", "Zenon", 0, "Krzywiski", "$2a$10$6NTqm.zz3H6wTlPbgR7lU.xGq0W.C9Ir5WgDZpADnSlANGi8yOCwW", 5)

---------------------------

INSERT INTO authority VALUES (null, 'ROLE_ADMIN');
INSERT INTO authority VALUES (null, 'ROLE_USER');

INSERT INTO employee_authority VALUES (1, 1);
INSERT INTO employee_authority VALUES (2, 2);
INSERT INTO employee_authority VALUES (3, 2);
INSERT INTO employee_authority VALUES (4, 2);
INSERT INTO employee_authority VALUES (5, 2);

----------------------------

INSERT INTO projects VALUES (null, "divorce of the client with his spouse; client demands alimony from his wife", "divorce", "2019/06/fam", 1000)
INSERT INTO projects VALUES (null, "client's wish is to sue his former employer for damages incurred during his employment time", "damages", "2017/01/civ", 1000)
INSERT INTO projects VALUES (null, "client is investigated by the tax office due to tax arrears in fiscal year 2018", "tax dispute", "2019/07/tax", 1001)
INSERT INTO projects VALUES (null, "merger of the parent company with its subsidiary in Cyprus", "merger", "2018/08/com", 1002)

INSERT INTO project_employee VALUES (1, 1)
INSERT INTO project_employee VALUES (1, 2)
INSERT INTO project_employee VALUES (2, 3)
INSERT INTO project_employee VALUES (2, 4)
INSERT INTO project_employee VALUES (3, 1)
INSERT INTO project_employee VALUES (3, 5)
INSERT INTO project_employee VALUES (3, 3)
INSERT INTO project_employee VALUES (4, 1)
INSERT INTO project_employee VALUES (4, 2)
INSERT INTO project_employee VALUES (4, 3)
INSERT INTO project_employee VALUES (4, 5)

INSERT INTO timesheet_week VALUES (null, "2019-08-05", 4, 5, null, null, 3, 7, 2)
INSERT INTO timesheet_week VALUES (null, "2019-08-05", 4, 3, null, null, 5, 1, 6)
INSERT INTO timesheet_week VALUES (null, "2019-08-05", 1, 3, null, null, 3, 7, 4)
INSERT INTO timesheet_week VALUES (null, "2019-08-05", 7, 5, null, null, 5, 1, 4)
INSERT INTO timesheet_week VALUES (null, "2019-08-05", 1, 3, null, null, 3, 6, 8)
INSERT INTO timesheet_week VALUES (null, "2019-08-05", 7, 5, null, null, 5, 2, 1)
INSERT INTO timesheet_week VALUES (null, "2019-08-05", 3, 2, null, null, 3, 7, 2)
INSERT INTO timesheet_week VALUES (null, "2019-08-05", 5, 6, null, null, 5, 1, 6)
INSERT INTO timesheet_week VALUES (null, "2019-08-05", 4, 3, null, null, 2, 4, 3)
INSERT INTO timesheet_week VALUES (null, "2019-08-05", 4, 5, null, null, 6, 4, 5)
INSERT INTO timesheet_week VALUES (null, "2019-08-12", 4, 5, null, null, 6, 4, 5)
INSERT INTO timesheet_week VALUES (null, "2019-08-12", 4, 3, null, null, 2, 4, 3)
INSERT INTO timesheet_week VALUES (null, "2019-08-12", 4, 5, null, null, 6, 4, 5)
INSERT INTO timesheet_week VALUES (null, "2019-08-12", 4, 3, null, null, 2, 4, 3)
INSERT INTO timesheet_week VALUES (null, "2019-08-12", 4, 5, null, null, 6, 4, 5)
INSERT INTO timesheet_week VALUES (null, "2019-08-12", 4, 3, null, null, 2, 4, 3)
INSERT INTO timesheet_week VALUES (null, "2019-08-12", 4, 5, null, null, 6, 4, 5)
INSERT INTO timesheet_week VALUES (null, "2019-08-12", 4, 3, null, null, 2, 4, 3)
INSERT INTO timesheet_week VALUES (null, "2019-08-12", 4, 5, null, null, 6, 4, 5)
INSERT INTO timesheet_week VALUES (null, "2019-08-12", 4, 3, null, null, 2, 4, 3)

INSERT INTO timesheet_reference_unit VALUES (null, 1, 1, 1)
INSERT INTO timesheet_reference_unit VALUES (null, 1, 2, 2)
INSERT INTO timesheet_reference_unit VALUES (null, 2, 1, 3)
INSERT INTO timesheet_reference_unit VALUES (null, 2, 2, 4)
INSERT INTO timesheet_reference_unit VALUES (null, 3, 3, 5)
INSERT INTO timesheet_reference_unit VALUES (null, 3, 4, 6)
INSERT INTO timesheet_reference_unit VALUES (null, 4, 3, 7)
INSERT INTO timesheet_reference_unit VALUES (null, 4, 4, 8)
INSERT INTO timesheet_reference_unit VALUES (null, 5, 3, 9)
INSERT INTO timesheet_reference_unit VALUES (null, 5, 4, 10)
INSERT INTO timesheet_reference_unit VALUES (null, 5, 1, 11)
INSERT INTO timesheet_reference_unit VALUES (null, 5, 2, 12)
INSERT INTO timesheet_reference_unit VALUES (null, 4, 3, 13)
INSERT INTO timesheet_reference_unit VALUES (null, 4, 4, 14)
INSERT INTO timesheet_reference_unit VALUES (null, 3, 1, 15)
INSERT INTO timesheet_reference_unit VALUES (null, 3, 2, 16)
INSERT INTO timesheet_reference_unit VALUES (null, 2, 3, 17)
INSERT INTO timesheet_reference_unit VALUES (null, 2, 4, 18)
INSERT INTO timesheet_reference_unit VALUES (null, 1, 2, 19)
INSERT INTO timesheet_reference_unit VALUES (null, 1, 3, 20)









