-- Sally supervises Eve & Earl
insert into employee
    (id,version,first_name,last_name,username,password,supervisor)
    values(100,0,'Sally','Supervior','sally','password',null);

insert into employee
    (id,version,first_name,last_name,username,password,supervisor)
    values(110,0,'Eve','Employee','eve','password',100);
insert into employee
    (id,version,first_name,last_name,username,password,supervisor)
    values(120,0,'Earl','Employee','earl','password',100);

insert into expense
    (id,version,description,expense_date,ammount,reporter,approved)
    values(111,0,'QCon Airfare',CURRENT_DATE,450,110,false);
insert into expense
    (id,version,description,expense_date,ammount,reporter,approved)
    values(112,0,'Dinner',CURRENT_DATE,35.50,110,false);

insert into expense
    (id,version,description,expense_date,ammount,reporter,approved)
    values(121,0,'Gas',CURRENT_DATE,55.78,120,false);

-- Scott supervices Aaron & Allison
insert into employee
    (id,version,first_name,last_name,username,password,supervisor)
    values(200,0,'Scott','Supervior','scott','password',null);

insert into employee
    (id,version,first_name,last_name,username,password,supervisor)
    values(210,0,'Aaron','Associate','aaron','password',200);
insert into employee
    (id,version,first_name,last_name,username,password,supervisor)
    values(220,0,'Allison','Associate','allison','password',200);

insert into expense
    (id,version,description,expense_date,ammount,reporter,approved)
    values(211,0,'Parking',CURRENT_DATE,100,210,false);
insert into expense
    (id,version,description,expense_date,ammount,reporter,approved)
    values(212,0,'Taxi',CURRENT_DATE,75,210,false);

insert into expense
    (id,version,description,expense_date,ammount,reporter,approved)
    values(221,0,'Hotel',CURRENT_DATE,699.99,220,false);