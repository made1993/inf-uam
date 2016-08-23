alter table customers add promo integer;

CREATE OR REPLACE FUNCTION promo() returns TRIGGER
language plpgsql
as $$
DECLARE
BEGIN
update orders set totalamount =totalamount- totalamount * promo/100
	from customers
	join orders using (customerid)
	where customerid= NEW.customerid and status = null;
	return new;
	perform pg_sleep(30);
END;
$$;

create trigger updPromo after update or insert on customers
for each row execute procedure promo();

