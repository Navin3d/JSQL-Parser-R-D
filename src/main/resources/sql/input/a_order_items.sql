-- Add a foreign key constraint to the 'order_items' table referencing the 'orders' table
ALTER TABLE order_items
ADD CONSTRAINT fk_order
FOREIGN KEY (order_id) REFERENCES orders(order_id);
