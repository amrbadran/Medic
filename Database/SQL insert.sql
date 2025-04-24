INSERT INTO medicinewarehouse.supplier
(id, phone, email, address, name, scope)
VALUES
(83472619, 15559876, 'john.doe@email.com', '456 Oak St, Townsville, State, 54321', 'John Doe', 'Local'),
(61928374, 15555678, 'jane.smith@email.com', '789 Pine St, Villagetown, State, 32109', 'Jane Smith', 'Global'),
(12457893, 15554321, 'alex.jones@email.com', '321 Elm St, Hamletville, State, 09876', 'Alex Jones', 'Local'),
(87543921, 15558765, 'emily.white@email.com', '987 Maple St, Cityburg, State, 65432', 'Emily White', 'Global'),
(39218765, 15553456, 'mike.brown@email.com', '654 Birch St, Countryside, State, 21098', 'Mike Brown', 'Local');

INSERT INTO medicinewarehouse.customer
(id, phone, email, address, name, discount)
VALUES
(56781234, 15557890, 'lisa.green@email.com', '789 Cedar St, Hamletsville, State, 89012', 'Lisa Green', 5),
(90817236, 15556543, 'chris.taylor@email.com', '876 Pine St, Villageville, State, 54321', 'Chris Taylor', 10),
(23456789, 15558901, 'sarah.jones@email.com', '234 Oak St, Citytown, State, 10987', 'Sarah Jones',11),
(65432109, 15552345, 'david.white@email.com', '345 Maple St, Townburg, State, 87654', 'David White',2),
(78901234, 15553210, 'amy.smith@email.com', '456 Birch St, Citytown, State, 54321', 'Amy Smith',4);


INSERT INTO medicinewarehouse.catagory (catagoryid, cname, description)
VALUES
(1119, 'Pain Relief/Fever Reducer', 'Medications in this category are commonly used to relieve pain and reduce fever. They are often referred to as analgesics.'),
(1120, 'Cholesterol-Lowering Medication (Statins)', 'This category includes medications, specifically statins, designed to lower cholesterol levels in the blood, reducing the risk of cardiovascular events.'),
(1121, 'Antihistamine/Allergy Relief', 'Antihistamines are medications that help alleviate symptoms of allergies, such as itching, sneezing, and runny nose, by blocking the effects of histamine.'),
(1122, 'NSAID/Pain Relief', 'NSAIDs are a class of medications that provide pain relief and reduce inflammation. They are commonly used to treat conditions like arthritis and other inflammatory disorders.'),
(1123, 'PPI/Gastrointestinal Medication', 'PPIs are medications that reduce the production of stomach acid. They are used to treat conditions such as gastroesophageal reflux disease (GERD) and peptic ulcers.');


alter table medicinewarehouse.medicine
alter column unit type varchar(100);

INSERT INTO medicinewarehouse.medicine (medicineid, brandname,scentificname, unit, exdate, quantity, taxpercantage, pricebtax,catagoryid)
VALUES
(119,'Panadol', 'Acetaminophen', 'Tablet', '2023-12-31', 100, 5, 10.00,1119),
(120,'Lipitor', 'Atorvastatin', 'Capsule', '2023-11-30', 30, 8, 25.50,1120),
(121,'Zyrtec', 'Cetirizine', 'Syrup', '2024-02-28', 150, 7, 15.75,1121),
(122,'Advil', 'Ibuprofen', 'Tablet', '2023-10-15', 50, 6, 8.99,1122),
(123,'Nexium', 'Esomeprazole', 'Capsule', '2024-01-15', 60, 9, 18.75,1123);


INSERT INTO medicinewarehouse.supplies (tid, tdate, status, supplycost, supplyquantity,supplierid,medicineid)
VALUES
(1001, '2023-05-15', 'PAID', 500.00, 100,83472619,119),
(1002, '2023-06-20', 'PAID', 700.50, 75,61928374,120),
(1003, '2023-07-10', 'UNPAID', 300.25, 50,12457893,121),
(1004, '2023-08-05', 'UNPAID', 900.75, 120,87543921,122),
(1005, '2023-09-12', 'UNPAID', 450.80, 80,39218765,123);


INSERT INTO medicinewarehouse.medicinedistrbuter (mdestrbuiterid, bonuspercentage)
VALUES
(1011, 0.2),
(2012, 0.3),
(3013, 0.4),
(4014, 0.01),
(5015, 0.02);


INSERT INTO medicinewarehouse.employee (ssn, fname, mname, lname, birthdate, salaryperhour, sex)
VALUES
(1011, 'Alice', 'Marie', 'Johnson', '1990-03-15', 25.00, 'female'),
(2012, 'John', 'Michael', 'Smith', '1985-07-22', 30.00, 'male'),
(3013, 'Emily', 'Rose', 'Davis', '1992-11-10', 28.50, 'female'),
(4014, 'James', 'Patrick', 'White', '1988-05-03', 32.50, 'male'),
(5015, 'Sarah', 'Elizabeth', 'Turner', '1995-09-18', 26.75, 'female');


INSERT INTO medicinewarehouse.orders (tid, tdate, status, paymentmethod,orderquantity,customerid,MSSN,medicineid)
VALUES
(2001, '2023-05-15', 'UNPAID', 'Check',3,56781234,1011,119),
(2002, '2023-06-20', 'UNPAID', 'Cash',5,90817236,1011,120),
(2003, '2023-07-10', 'PAID', 'Check',2,56781234,5015,119),
(2004, '2023-08-05', 'PAID', 'Cash',3,65432109,4014,123),
(2005, '2023-09-12', 'UNPAID', 'Check',1,78901234,3013,122);

INSERT INTO medicinewarehouse.expense (expenseid, expensedate, expensetype, description, amount)
VALUES
(2101, '2023-05-15', 'Inventory Purchase', 'Restocking various medicines', 10000),
(2102, '2023-06-20', 'Equipment Maintenance', 'Repair and maintenance of storage shelves', 800),
(2103, '2023-07-10', 'Utilities', 'Payment for electricity and water', 500),
(2104, '2023-08-05', 'Employee Salaries', 'Monthly salary disbursement for warehouse staff', 15000),
(2105, '2023-09-12', 'Packaging Supplies', 'Purchase of boxes and packaging materials', 1200);




/**/   