-- Modify the primary key of the 'students' table to include an auto-incrementing column
ALTER TABLE students
DROP CONSTRAINT students_pkey;

ALTER TABLE students
ADD COLUMN student_id SERIAL PRIMARY KEY;
