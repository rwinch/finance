-- Spring supervises Edith & Edward
insert into employee
    (id,version,first_name,last_name,username,password,supervisor)
    values(100,0,'Spring','Supervisor','spring','password',null);

insert into employee
    (id,version,first_name,last_name,username,password,supervisor)
    values(120,0,'Edith','Employee','edith','password',100);
insert into employee
    (id,version,first_name,last_name,username,password,supervisor)
    values(110,0,'Edward','Employee','edward','password',100);

insert into expense
    (id,version,description,expense_date,ammount,reporter,approved)
    values(111,0,'SpringOne Airfare',CURRENT_DATE,450,110,false);
insert into expense
    (id,version,description,expense_date,ammount,reporter,approved)
    values(112,0,'Dinner',CURRENT_DATE,35.50,110,false);

insert into expense
    (id,version,description,expense_date,ammount,reporter,approved)
    values(121,0,'Gas',CURRENT_DATE,55.78,120,false);

-- Sam supervises Alex & Alyssa
insert into employee
    (id,version,first_name,last_name,username,password,supervisor)
    values(200,0,'Sam','Supervisor','sam','password',null);

insert into employee
    (id,version,first_name,last_name,username,password,supervisor)
    values(210,0,'Alex','Associate','alex','password',200);
insert into employee
    (id,version,first_name,last_name,username,password,supervisor)
    values(220,0,'Alyssa','Associate','alyssa','password',200);

insert into expense
    (id,version,description,expense_date,ammount,reporter,approved)
    values(211,0,'Parking',CURRENT_DATE,100,210,false);
insert into expense
    (id,version,description,expense_date,ammount,reporter,approved)
    values(212,0,'Taxi',CURRENT_DATE,75,210,false);

insert into expense
    (id,version,description,expense_date,ammount,reporter,approved)
    values(221,0,'Hotel',CURRENT_DATE,699.99,220,false);