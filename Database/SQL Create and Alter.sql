
create domain medicineWarehouse.scope_domain as char(6) check (VALUE in ('Global','Local'));

create table medicineWarehouse.Supplier(
	
ID integer primary key,
Phone numeric(12,0) not null,
Email varchar(320) not null,
Address varchar(40),
Name varchar(255) not null,
Scope medicineWarehouse.scope_domain not null
);

create table medicineWarehouse.Customer(
	
ID integer primary key,
Phone numeric(12,0) not null,
Email varchar(320) not null,
Address varchar(40) not null,
Name varchar(255) not null,
Discount integer	
);

create table medicineWarehouse.Supplies(
TID integer primary key,
TDate timestamp DEFAULT CURRENT_TIMESTAMP,
Status char(6) check (Status in ('UNPAID','PAID')) DEFAULT 'UNPAID',
SupplyCost numeric check(SupplyCost >= 0) not null,
SupplyQuantity integer not null
);

create table medicineWarehouse.Orders(
TID integer primary key,
TDate timestamp DEFAULT CURRENT_TIMESTAMP,
Status char(6) check (Status in ('UNPAID','PAID')) DEFAULT 'UNPAID',
PaymentMethod char(5) check (PaymentMethod in ('Cash','Check')) DEFAULT 'Check' not null,
OrderQuantity integer not null
);

create table medicineWarehouse.Medicine(
MedicineID integer primary key,
BrandName varchar(100),
ScentificName varchar(100),
Unit integer ,
ExDate date ,
Quantity integer ,
TaxPercantage real ,
PriceBTax numeric 
);

create table medicineWarehouse.Catagory(
CatagoryID integer primary key,
CName varchar(100),
Discription varchar(100)
);

create table medicineWarehouse.Employee(
SSN integer primary key ,
FName varchar(20),
MName varcahr(20),
LName varchar(20),
BirthDate date ,
SalaryPerHour integer,
Sex varchar(10) check (gender in ('female', 'male'))
);

create table medicineWarehouse.MDestrubuter(
	MDestrubuterID integer primary key,
	BonusPersange real
);

create table medicineWarehouse.Expense(
	ExpenseID integer primary key ,
	ExpenseDate date ,
	ExpenseType varchar (20),
	Description varchar (100),
	Amount integer
);

lter table medicinewarehouse.supplies
add column SupplierID integer;

alter table medicinewarehouse.supplies
add column MedicineID integer;

alter table medicinewarehouse.orders
add column CustomerID integer;

alter table medicinewarehouse.orders
add column MSSN integer;

alter table medicinewarehouse.orders
add column MedicineID integer;

alter table medicinewarehouse.medicine
add column CatagoryID integer;


alter table medicinewarehouse.supplies
add foreign key (SupplierID)
references medicinewarehouse.supplier(ID);

alter table medicinewarehouse.supplies
add foreign key (MedicineID)
references medicinewarehouse.medicine(MedicineID);

alter table medicinewarehouse.medicine
add foreign key (catagoryID)
references medicinewarehouse.catagory(CatagoryID);

alter table medicinewarehouse.orders
add foreign key (CustomerID)
references medicinewarehouse.customer(ID);

alter table medicinewarehouse.orders
add foreign key (MSSN)
references medicinewarehouse.mdestrubuter(MdestrubuterID);

alter table medicinewarehouse.orders
add foreign key (MedicineID)
references medicinewarehouse.medicine(MedicineID);

