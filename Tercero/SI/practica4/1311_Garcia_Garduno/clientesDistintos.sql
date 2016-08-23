select count(num)from (select count(*)as num
from orders
where totalamount >=100
and extract (year from orderdate)=2010 and extract (month from orderdate) =4
group by extract (year from orderdate),extract (month from orderdate) ,customerid) as a;

explain select count(num)from (select count(*)as num
from orders
where totalamount >=100
and extract (year from orderdate)=2010 and extract (month from orderdate) =4
group by extract (year from orderdate),extract (month from orderdate) ,customerid) as a;

CREATE INDEX idx_totalamount on orders(totalamount);

explain select count(num)from (select count(*)as num
from orders
where totalamount >=100
and extract (year from orderdate)=2010 and extract (month from orderdate) =4
group by extract (year from orderdate),extract (month from orderdate) ,customerid) as a;

CREATE INDEX idx_customerid on orders(customerid);

explain select count(num)from (select count(*)as num
from orders
where totalamount >=100
and extract (year from orderdate)=2010 and extract (month from orderdate) =4
group by extract (year from orderdate),extract (month from orderdate) ,customerid) as a;

CREATE INDEX idx_orderdate on orders(orderdate);

explain select count(num)from (select count(*)as num
from orders
where totalamount >=100
and extract (year from orderdate)=2010 and extract (month from orderdate) =4
group by extract (year from orderdate),extract (month from orderdate) ,customerid) as a;
