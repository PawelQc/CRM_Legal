INSERT INTO additional_info_clients VALUES (null, "ul. Alberta 3/43, 01-577 Warszawa", "77249000056420077578477743", "kulczyk.j@gmail.com", 550, "9318235090", "879876543")
INSERT INTO additional_info_clients VALUES (null, "ul. Krecia 2, 01-343 Warszawa", "86106000058780488477816280", "tarcz.and@onet.pl", 480, "8943064667", "564432123")
INSERT INTO additional_info_clients VALUES (null, "ul. Zamszy 7, 01-434 Warszawa", "59150017356396376097274923", "info.polexport@polex.pl", 700, "1219027734", "512098876")

--TODO client ma id.auto -->jak nadam id od 1 to mogą się powtórzyć (przy dodawaniu nalicza od 1) -->można tu jakoś zaradzić?
INSERT INTO clients_natural_person VALUES (1000, 1, "Janusz", "Kulczyk")
INSERT INTO clients_natural_person VALUES (1001, 2, "Andrzej", "Tarczynski")
INSERT INTO clients_legal_person VALUES (1002, 3, "PolExport sp. z o.o.")

INSERT INTO additional_info_employees VALUES (null, 500, 300, "546434654", 60000)
INSERT INTO additional_info_employees VALUES (null, 400, 100, "634567987", 20000)
INSERT INTO additional_info_employees VALUES (null, 300, 60, "765456987", 12000)
INSERT INTO additional_info_employees VALUES (null, 200, 40, "546768987", 9000)
INSERT INTO additional_info_employees VALUES (null, 600, 400, "654765876", 80000)

INSERT INTO employees VALUES (null, "adam.kowalski@PKlegal.pl", "Adam", 0, "Kowalski", "12341234", 1)
INSERT INTO employees VALUES (null, "k.janicki@PKlegal.pl", "Krzysztof", 0, "Janicki", "12341234", 2)
INSERT INTO employees VALUES (null, "patryk.zubrzycki@PKlegal.pl", "Patryk", 0, "Zubrzycki", "12341234", 3)
INSERT INTO employees VALUES (null, "pawel.janiak@PKlegal.pl", "Pawel", 0, "Janiak", "12341234", 4)
INSERT INTO employees VALUES (null, "zenon.krzywiski@PKlegal.pl", "Zenon", 1, "Krzywiski", "12341234", 5)

INSERT INTO cases VALUES (null, "divorce of the client with his spouse; client demands alimony from his wife", "divorce", "2019/06/fam", 1000)
INSERT INTO cases VALUES (null, "client's wish is to sue his former employer for damages incurred during his employment time", "damages", "2017/01/civ", 1000)
INSERT INTO cases VALUES (null, "client is investigated by the tax office due to tax arrears in fiscal year 2018", "tax dispute", "2019/07/tax", 1001)
INSERT INTO cases VALUES (null, "merger of the parent company with its subsidiary in Cyprus", "merger", "2018/08/com", 1002)

INSERT INTO case_employee VALUES (1, 1)
INSERT INTO case_employee VALUES (1, 2)
INSERT INTO case_employee VALUES (2, 3)
INSERT INTO case_employee VALUES (2, 4)
INSERT INTO case_employee VALUES (3, 1)
INSERT INTO case_employee VALUES (3, 5)
INSERT INTO case_employee VALUES (3, 3)
INSERT INTO case_employee VALUES (4, 1)
INSERT INTO case_employee VALUES (4, 2)
INSERT INTO case_employee VALUES (4, 3)
INSERT INTO case_employee VALUES (4, 5)





