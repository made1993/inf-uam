--b) 
explain select count(*) from orders where status is null;

explain select count(*) from orders where status ='Shipped';

--c)
CREATE INDEX idx_status on orders(status);

--d) 
explain select count(*) from orders where status is null;

explain select count(*) from orders where status ='Shipped';

--e)

ANALYZE orders;

--f)
explain select count(*) from orders where status is null;

explain select count(*) from orders where status ='Shipped';

--g)
explain select count(*) from orders where status ='Paid';
explain select count(*) from orders where status ='Processed';
