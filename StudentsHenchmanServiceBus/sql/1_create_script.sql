#use henchman; #kazdy musi sobie usatwic swoja nazwe schematu
SET foreign_key_checks = 0;

drop table if exists PLAN_MAPPING;
drop table if exists DEPARTMENT;
drop table if exists FIELD;
drop table if exists DEAN_GROUP;
drop table if exists BUILD;
drop table if exists DATE;
drop table if exists ROOM;
drop table if exists SPECIALIZATION;
drop table if exists COURSE_TYPE;
drop table if exists SUBJECT;
drop table if exists FIELD_SUBJECT;
drop table if exists TEACHER;


CREATE TABLE `PLAN_MAPPING` (
  `Ref` varchar(100) DEFAULT NULL,
  `Day` varchar(100) DEFAULT NULL,
  `Time` varchar(100) DEFAULT NULL,
  `Weeks` varchar(100) DEFAULT NULL,
  `EventCat` varchar(100) DEFAULT NULL,
  `Weighting` varchar(100) DEFAULT NULL,
  `Module` varchar(100) DEFAULT NULL,
  `ModCode` varchar(100) DEFAULT NULL,
  `Room` varchar(100) DEFAULT NULL,
  `Surname` varchar(100) DEFAULT NULL,
  `Forenames` varchar(100) DEFAULT NULL,
  `StaffCode` varchar(100) DEFAULT NULL,
  `Group` varchar(100) DEFAULT NULL,
  `Student` varchar(100) DEFAULT NULL,
  `StudCode` varchar(100) DEFAULT NULL,
  `Protected` varchar(100) DEFAULT NULL,
  `Global` varchar(100) DEFAULT NULL,
  `Description` varchar(100) DEFAULT NULL,
  `DateChanged` varchar(100) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2469 DEFAULT CHARSET=utf8;


CREATE TABLE `DEPARTMENT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;


CREATE TABLE `FIELD` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `DEPARTMENT_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_hh72378lgxdmb49m0` (`DEPARTMENT_ID`),
  CONSTRAINT `FK_hh72378lgxdmb49m0` FOREIGN KEY (`DEPARTMENT_ID`) REFERENCES `department` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;


CREATE TABLE `DEAN_GROUP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ABBREVIATION` varchar(255) NOT NULL,
  `DEGREE` int(11) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `TERM` int(11) NOT NULL,
  `FIELD_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_jvw32tgk6buimr8i3jncvcyke` (`ABBREVIATION`),
  KEY `FK_k7qkhohiuplxrgtqd10fblr` (`FIELD_ID`),
  CONSTRAINT `FK_k7qkhohiuplxrgtqd10fblr` FOREIGN KEY (`FIELD_ID`) REFERENCES `field` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8;


CREATE TABLE `BUILD` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `LATITUDE` double DEFAULT NULL,
  `LONGITUDE` double DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_ec9eug3irmodmalsunrtutg9` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `DATE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CYCLE` int(11) NOT NULL,
  `DATE` date NOT NULL,
  `DAY_OF_WEEK` varchar(255) NOT NULL,
  `WEEK_NO` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_ao4pxo3mp65jj1t0wgo4557b` (`DATE`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;


CREATE TABLE `ROOM` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `SPECIALIZATION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  `FIELD_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_4r7widpmh2cdh7pvw2f854ms` (`FIELD_ID`),
  CONSTRAINT `FK_4r7widpmh2cdh7pvw2f854ms` FOREIGN KEY (`FIELD_ID`) REFERENCES `field` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `COURSE_TYPE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `TYPE` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_s097ub0jnw9mfncm858k6vnb` (`TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `SUBJECT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(255) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_de8h7o6ncpxchba7jjvagsvv` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `FIELD_SUBJECT` (
  `SUBJECT_ID` bigint(20) NOT NULL,
  `FIELD_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`FIELD_ID`,`SUBJECT_ID`),
  KEY `FK_rnwuk7rhowi9p8n02iulqfn4` (`SUBJECT_ID`),
  CONSTRAINT `FK_96pus40qrhrggje8r98wnrv7` FOREIGN KEY (`FIELD_ID`) REFERENCES `field` (`ID`),
  CONSTRAINT `FK_rnwuk7rhowi9p8n02iulqfn4` FOREIGN KEY (`SUBJECT_ID`) REFERENCES `subject` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `TEACHER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `EMAIL` varchar(255),
  `NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_al3ku1wrpfx7sc1py7vn99do` (`EMAIL`),
  UNIQUE KEY `UK_sj49cde2ewvr2t1vb0x1uro3` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET foreign_key_checks = 1;