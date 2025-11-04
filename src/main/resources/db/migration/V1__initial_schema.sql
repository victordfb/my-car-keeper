-- Create cars table
CREATE TABLE cars (
                      id VARCHAR(255) PRIMARY KEY,
                      make VARCHAR(255) NOT NULL,
                      model VARCHAR(255) NOT NULL,
                      year INTEGER NOT NULL,
                      vin VARCHAR(255) NOT NULL UNIQUE
);

-- Create parts table
CREATE TABLE parts (
                       id VARCHAR(255) PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       car_id VARCHAR(255) NOT NULL,
                       CONSTRAINT fk_parts_car FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE
);

-- Create index on car_id for parts
CREATE INDEX idx_parts_car_id ON parts(car_id);

-- Create odometer_readings table
CREATE TABLE odometer_readings (
                                   id VARCHAR(255) PRIMARY KEY,
                                   data_time TIMESTAMP NOT NULL,
                                   reading BIGINT NOT NULL,
                                   car_id VARCHAR(255) NOT NULL,
                                   CONSTRAINT fk_odometer_readings_car FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE
);

-- Create index on car_id for odometer_readings
CREATE INDEX idx_odometer_readings_car_id ON odometer_readings(car_id);

-- Create part_changes table
CREATE TABLE part_changes (
                              id BIGSERIAL PRIMARY KEY,
                              car_id VARCHAR(255) NOT NULL,
                              part_id VARCHAR(255) NOT NULL,
                              moment TIMESTAMP NOT NULL,
                              odometer BIGINT NOT NULL,
                              odometer_expiration BIGINT NOT NULL,
                              duration_seconds BIGINT NOT NULL,
                              CONSTRAINT fk_part_changes_car FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE
);

-- Create indexes on car_id and part_id for part_changes
CREATE INDEX idx_part_changes_car_id ON part_changes(car_id);
CREATE INDEX idx_part_changes_part_id ON part_changes(part_id);
