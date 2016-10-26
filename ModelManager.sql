-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema ModelManagerBD
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `ModelManagerBD` ;

-- -----------------------------------------------------
-- Schema ModelManagerBD
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ModelManagerBD` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
USE `ModelManagerBD` ;

-- -----------------------------------------------------
-- Table `ModelManagerBD`.`role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ModelManagerBD`.`role` ;

CREATE TABLE IF NOT EXISTS `ModelManagerBD`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;

INSERT INTO `role` (`role_description`) VALUES
('System Administrator'),
('Wealth Manager'),
('Client');


-- -----------------------------------------------------
-- Table `ModelManagerBD`.`user_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ModelManagerBD`.`user_status` ;

CREATE TABLE IF NOT EXISTS `ModelManagerBD`.`user_status` (
  `status_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`status_id`))
ENGINE = InnoDB;

INSERT INTO `user_status` (`description`) VALUES
('Active'),
('Inactive');


-- -----------------------------------------------------
-- Table `ModelManagerBD`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ModelManagerBD`.`user` ;

CREATE TABLE IF NOT EXISTS `ModelManagerBD`.`user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role_role_id` INT NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `initial_pass` VARCHAR(4) NOT NULL DEFAULT 'YES',
  `user_alt_pass` VARCHAR(4) NOT NULL DEFAULT 'NO',
  `username` VARCHAR(45) NOT NULL,
  `status_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_user_role_idx` (`role_role_id` ASC),
  INDEX `fk_user_user_status1_idx` (`status_id` ASC),
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_role_id`)
    REFERENCES `ModelManagerBD`.`role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_user_status1`
    FOREIGN KEY (`status_id`)
    REFERENCES `ModelManagerBD`.`user_status` (`status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

INSERT INTO `user` (`name`, `password`, `role_role_id`, `username`,`status_id`) VALUES
('Johnny', 'F7CLfLXoJVjpshWSch8zWyQuF60AACmtYbI06unIOFo=', 1, 'john', 1);


-- -----------------------------------------------------
-- Table `ModelManagerBD`.`security`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ModelManagerBD`.`security` ;

CREATE TABLE IF NOT EXISTS `ModelManagerBD`.`security` (
  `security_id` INT(10) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `unit_price` DECIMAL(10,2) NOT NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` INT(11) NOT NULL,
  PRIMARY KEY (`security_id`),
  INDEX `fk_security_user1_idx` (`created_by` ASC),
  CONSTRAINT `fk_security_user1`
    FOREIGN KEY (`created_by`)
    REFERENCES `ModelManagerBD`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ModelManagerBD`.`model`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ModelManagerBD`.`model` ;

CREATE TABLE IF NOT EXISTS `ModelManagerBD`.`model` (
  `model_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL,
  `created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` INT(11) NOT NULL,
  PRIMARY KEY (`model_id`),
  INDEX `fk_model_structure_user1_idx` (`created_by` ASC),
  CONSTRAINT `fk_model_structure_user1`
    FOREIGN KEY (`created_by`)
    REFERENCES `ModelManagerBD`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ModelManagerBD`.`portfolio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ModelManagerBD`.`portfolio` ;

CREATE TABLE IF NOT EXISTS `ModelManagerBD`.`portfolio` (
  `portforlio_id` INT(15) NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL,
  `created` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` INT(11) NOT NULL,
  `model_id` INT(11) NULL,
  `current_amount` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`portforlio_id`),
  INDEX `fk_portfolio_user1_idx` (`created_by` ASC),
  INDEX `fk_portfolio_model1_idx` (`model_id` ASC),
  CONSTRAINT `fk_portfolio_user1`
    FOREIGN KEY (`created_by`)
    REFERENCES `ModelManagerBD`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_portfolio_model1`
    FOREIGN KEY (`model_id`)
    REFERENCES `ModelManagerBD`.`model` (`model_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ModelManagerBD`.`model_details`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ModelManagerBD`.`model_details` ;

CREATE TABLE IF NOT EXISTS `ModelManagerBD`.`model_details` (
  `model_id` INT(11) NOT NULL,
  `security_id` INT(10) NOT NULL,
  `percentage` DECIMAL(4,2) NOT NULL,
  INDEX `fk_model_details_model1_idx` (`model_id` ASC),
  INDEX `fk_model_details_security1_idx` (`security_id` ASC),
  PRIMARY KEY (`model_id`, `security_id`),
  CONSTRAINT `fk_model_details_model1`
    FOREIGN KEY (`model_id`)
    REFERENCES `ModelManagerBD`.`model` (`model_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_model_details_security1`
    FOREIGN KEY (`security_id`)
    REFERENCES `ModelManagerBD`.`security` (`security_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
