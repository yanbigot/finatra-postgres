CREATE SCHEMA IF NOT EXISTS BDD_SCHEMA;

DROP TABLE IF EXISTS BDD_SCHEMA.MANAGER_COLLABORATOR;
DROP TABLE IF EXISTS BDD_SCHEMA.EMPLOYEES;
DROP TABLE IF EXISTS BDD_SCHEMA.FOLLOWUPS;
DROP TABLE IF EXISTS BDD_SCHEMA.TRAININGS;
DROP TABLE IF EXISTS BDD_SCHEMA.CURRICULUMS;

CREATE TABLE IF NOT EXISTS BDD_SCHEMA.MANAGER_COLLABORATOR(
  ggiManager varchar(20) NOT NULL,
  ggiCollaborator varchar(20) NOT NULL
 );
CREATE TABLE IF NOT EXISTS BDD_SCHEMA.EMPLOYEES(
merge_key varchar(20) NOT NULL,
data_map json NOT NULL
);
CREATE TABLE IF NOT EXISTS BDD_SCHEMA.FOLLOWUPS(
  merge_key varchar(20) NOT NULL,
  data_map json NOT NULL
 );
CREATE TABLE IF NOT EXISTS BDD_SCHEMA.TRAININGS(
  merge_key varchar(20) NOT NULL,
  data_map json NOT NULL
 );
 CREATE TABLE IF NOT EXISTS BDD_SCHEMA.CURRICULUMS(
  merge_key varchar(20) NOT NULL,
  data_map json NOT NULL
 );


INSERT INTO BDD_SCHEMA.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '10');
INSERT INTO BDD_SCHEMA.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '11');
INSERT INTO BDD_SCHEMA.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '12');
INSERT INTO BDD_SCHEMA.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '13');
INSERT INTO BDD_SCHEMA.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('2', '20');

INSERT INTO BDD_SCHEMA.EMPLOYEES(merge_key, data_map) VALUES('10', '{ "ggi": "10", "name": "name"}');
INSERT INTO BDD_SCHEMA.EMPLOYEES(merge_key, data_map) VALUES('11', '{ "ggi": "11", "name": "name"}');
INSERT INTO BDD_SCHEMA.EMPLOYEES(merge_key, data_map) VALUES('12', '{ "ggi": "12", "name": "name"}');
INSERT INTO BDD_SCHEMA.EMPLOYEES(merge_key, data_map) VALUES('13', '{ "ggi": "13", "name": "name"}');
INSERT INTO BDD_SCHEMA.EMPLOYEES(merge_key, data_map) VALUES('20', '{ "ggi": "20", "name": "name"}');

INSERT INTO BDD_SCHEMA.FOLLOWUPS(merge_key, data_map) VALUES('10', '{"ggi": "10", "trainingId": "t_10", "version": "1"}');
INSERT INTO BDD_SCHEMA.FOLLOWUPS(merge_key, data_map) VALUES('11', '{"ggi": "11", "trainingId": "t_11", "version": "1"}');
INSERT INTO BDD_SCHEMA.FOLLOWUPS(merge_key, data_map) VALUES('12', '{"ggi": "12", "trainingId": "t_12", "version": "1"}');
INSERT INTO BDD_SCHEMA.FOLLOWUPS(merge_key, data_map) VALUES('13', '{"ggi": "13", "trainingId": "t_13", "version": "1"}');
INSERT INTO BDD_SCHEMA.FOLLOWUPS(merge_key, data_map) VALUES('20', '{"ggi": "20", "trainingId": "t_20", "version": "1"}');

INSERT INTO BDD_SCHEMA.TRAININGS(merge_key, data_map) VALUES('t_10', '{ "trainingId": "t_10", "version": "1", "typeCode": "PRGM"}');
INSERT INTO BDD_SCHEMA.TRAININGS(merge_key, data_map) VALUES('t_11', '{ "trainingId": "t_11", "version": "1", "typeCode": "CRSE"}');
INSERT INTO BDD_SCHEMA.TRAININGS(merge_key, data_map) VALUES('t_12', '{ "trainingId": "t_12", "version": "1", "typeCode": "CRSE"}');
INSERT INTO BDD_SCHEMA.TRAININGS(merge_key, data_map) VALUES('t_13', '{ "trainingId": "t_13", "version": "1", "typeCode": "CRSE"}');
INSERT INTO BDD_SCHEMA.TRAININGS(merge_key, data_map) VALUES('t_20', '{ "trainingId": "t_20", "version": "1", "typeCode": "CRSE"}');

--INSERT INTBDD_SCHEMA.O curriculums(merge_key, data_map) VALUES('t_10', '{ "trainingId": "t_10", "curriculumId": "t_10"}}');
INSERT INTO BDD_SCHEMA.CURRICULUMS(merge_key, data_map) VALUES('t_11', '{ "trainingId": "t_11", "curriculumId": "t_10"}');
INSERT INTO BDD_SCHEMA.CURRICULUMS(merge_key, data_map) VALUES('t_12', '{ "trainingId": "t_12", "curriculumId": "t_10"}');
INSERT INTO BDD_SCHEMA.CURRICULUMS(merge_key, data_map) VALUES('t_13', '{ "trainingId": "t_13", "curriculumId": "t_10"}');
INSERT INTO BDD_SCHEMA.CURRICULUMS(merge_key, data_map) VALUES('t_20', '{ "trainingId": "t_20", "curriculumId": "t_10"}');