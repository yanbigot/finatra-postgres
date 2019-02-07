DROP TABLE IF EXISTS manager_collaborator;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS followups;
DROP TABLE IF EXISTS trainings;
DROP TABLE IF EXISTS curriculums;

CREATE TABLE IF NOT EXISTS MANAGER_COLLABORATOR(
  ggiManager varchar(20) NOT NULL,
  ggiCollaborator varchar(20) NOT NULL
 );
CREATE TABLE IF NOT EXISTS EMPLOYEES(
merge_key varchar(20) NOT NULL,
data_map json NOT NULL
);
CREATE TABLE IF NOT EXISTS FOLLOWUPS(
  merge_key varchar(20) NOT NULL,
  data_map json NOT NULL
 );
CREATE TABLE IF NOT EXISTS TRAININGS(
  merge_key varchar(20) NOT NULL,
  data_map json NOT NULL
 );
 CREATE TABLE IF NOT EXISTS CURRICULUMS(
  merge_key varchar(20) NOT NULL,
  data_map json NOT NULL
 );


INSERT INTO MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '10');
INSERT INTO MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '11');
INSERT INTO MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '12');
INSERT INTO MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '13');
INSERT INTO MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('2', '20');

INSERT INTO EMPLOYEES(merge_key, data_map) VALUES('10', '{ "ggi": "10", "name": "name"}');
INSERT INTO EMPLOYEES(merge_key, data_map) VALUES('11', '{ "ggi": "11", "name": "name"}');
INSERT INTO EMPLOYEES(merge_key, data_map) VALUES('12', '{ "ggi": "12", "name": "name"}');
INSERT INTO EMPLOYEES(merge_key, data_map) VALUES('13', '{ "ggi": "13", "name": "name"}');
INSERT INTO EMPLOYEES(merge_key, data_map) VALUES('20', '{ "ggi": "20", "name": "name"}');

INSERT INTO FOLLOWUPS(merge_key, data_map) VALUES('10', '{"ggi": "10", "trainingId": "t_10", "version": "1"}');
INSERT INTO FOLLOWUPS(merge_key, data_map) VALUES('11', '{"ggi": "11", "trainingId": "t_11", "version": "1"}');
INSERT INTO FOLLOWUPS(merge_key, data_map) VALUES('12', '{"ggi": "12", "trainingId": "t_12", "version": "1"}');
INSERT INTO FOLLOWUPS(merge_key, data_map) VALUES('13', '{"ggi": "13", "trainingId": "t_13", "version": "1"}');
INSERT INTO FOLLOWUPS(merge_key, data_map) VALUES('20', '{"ggi": "20", "trainingId": "t_20", "version": "1"}');

INSERT INTO TRAININGS(merge_key, data_map) VALUES('t_10', '{ "trainingId": "t_10", "version": "1", "typeCode": "PRGM"}');
INSERT INTO TRAININGS(merge_key, data_map) VALUES('t_11', '{ "trainingId": "t_11", "version": "1", "typeCode": "CRSE"}');
INSERT INTO TRAININGS(merge_key, data_map) VALUES('t_12', '{ "trainingId": "t_12", "version": "1", "typeCode": "CRSE"}');
INSERT INTO TRAININGS(merge_key, data_map) VALUES('t_13', '{ "trainingId": "t_13", "version": "1", "typeCode": "CRSE"}');
INSERT INTO TRAININGS(merge_key, data_map) VALUES('t_20', '{ "trainingId": "t_20", "version": "1", "typeCode": "CRSE"}');

--INSERT INTO curriculums(merge_key, data_map) VALUES('t_10', '{ "trainingId": "t_10", "curriculumId": "t_10"}}');
INSERT INTO CURRICULUMS(merge_key, data_map) VALUES('t_11', '{ "trainingId": "t_11", "curriculumId": "t_10"}');
INSERT INTO CURRICULUMS(merge_key, data_map) VALUES('t_12', '{ "trainingId": "t_12", "curriculumId": "t_10"}');
INSERT INTO CURRICULUMS(merge_key, data_map) VALUES('t_13', '{ "trainingId": "t_13", "curriculumId": "t_10"}');
INSERT INTO CURRICULUMS(merge_key, data_map) VALUES('t_20', '{ "trainingId": "t_20", "curriculumId": "t_10"}');