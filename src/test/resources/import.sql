c.createStatement().execute("CREATE IF NOT EXISTS SCHEMA bdd_schema;
c.createStatement().execute("
c.createStatement().execute("DROP TABLE IF EXISTS bdd_schema.MANAGER_COLLABORATOR;
c.createStatement().execute("DROP TABLE IF EXISTS bdd_schema.EMPLOYEES;
c.createStatement().execute("DROP TABLE IF EXISTS bdd_schema.FOLLOWUPS;
c.createStatement().execute("DROP TABLE IF EXISTS bdd_schema.TRAININGS;
c.createStatement().execute("DROP TABLE IF EXISTS bdd_schema.CURRICULUMS;
c.createStatement().execute("
c.createStatement().execute("CREATE TABLE IF NOT EXISTS bdd_schema.MANAGER_COLLABORATOR(
c.createStatement().execute("  ggiManager varchar(20) NOT NULL,
c.createStatement().execute("  ggiCollaborator varchar(20) NOT NULL
c.createStatement().execute(" );
c.createStatement().execute("CREATE TABLE IF NOT EXISTS bdd_schema.EMPLOYEES(
c.createStatement().execute("merge_key varchar(20) NOT NULL,
c.createStatement().execute("data_map json NOT NULL
c.createStatement().execute(");
c.createStatement().execute("CREATE TABLE IF NOT EXISTS bdd_schema.FOLLOWUPS(
c.createStatement().execute("  merge_key varchar(20) NOT NULL,
c.createStatement().execute("  data_map json NOT NULL
c.createStatement().execute(" );
c.createStatement().execute("CREATE TABLE IF NOT EXISTS bdd_schema.TRAININGS(
c.createStatement().execute("  merge_key varchar(20) NOT NULL,
c.createStatement().execute("  data_map json NOT NULL
c.createStatement().execute(" );
c.createStatement().execute(" CREATE TABLE IF NOT EXISTS bdd_schema.CURRICULUMS(
c.createStatement().execute("  merge_key varchar(20) NOT NULL,
c.createStatement().execute("  data_map json NOT NULL
c.createStatement().execute(" );
c.createStatement().execute("
c.createStatement().execute("
c.createStatement().execute("INSERT INTO bdd_schema.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '10');
c.createStatement().execute("INSERT INTO bdd_schema.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '11');
c.createStatement().execute("INSERT INTO bdd_schema.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '12');
c.createStatement().execute("INSERT INTO bdd_schema.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('1', '13');
c.createStatement().execute("INSERT INTO bdd_schema.MANAGER_COLLABORATOR(ggiManager, ggiCollaborator) VALUES('2', '20');
c.createStatement().execute("
c.createStatement().execute("INSERT INTO bdd_schema.EMPLOYEES(merge_key, data_map) VALUES('10', '{ "ggi": "10", "name": "name"}');
c.createStatement().execute("INSERT INTO bdd_schema.EMPLOYEES(merge_key, data_map) VALUES('11', '{ "ggi": "11", "name": "name"}');
c.createStatement().execute("INSERT INTO bdd_schema.EMPLOYEES(merge_key, data_map) VALUES('12', '{ "ggi": "12", "name": "name"}');
c.createStatement().execute("INSERT INTO bdd_schema.EMPLOYEES(merge_key, data_map) VALUES('13', '{ "ggi": "13", "name": "name"}');
c.createStatement().execute("INSERT INTO bdd_schema.EMPLOYEES(merge_key, data_map) VALUES('20', '{ "ggi": "20", "name": "name"}');
c.createStatement().execute("
c.createStatement().execute("INSERT INTO bdd_schema.FOLLOWUPS(merge_key, data_map) VALUES('10', '{"ggi": "10", "trainingId": "t_10", "version": "1"}');
c.createStatement().execute("INSERT INTO bdd_schema.FOLLOWUPS(merge_key, data_map) VALUES('11', '{"ggi": "11", "trainingId": "t_11", "version": "1"}');
c.createStatement().execute("INSERT INTO bdd_schema.FOLLOWUPS(merge_key, data_map) VALUES('12', '{"ggi": "12", "trainingId": "t_12", "version": "1"}');
c.createStatement().execute("INSERT INTO bdd_schema.FOLLOWUPS(merge_key, data_map) VALUES('13', '{"ggi": "13", "trainingId": "t_13", "version": "1"}');
c.createStatement().execute("INSERT INTO bdd_schema.FOLLOWUPS(merge_key, data_map) VALUES('20', '{"ggi": "20", "trainingId": "t_20", "version": "1"}');
c.createStatement().execute("
c.createStatement().execute("INSERT INTO bdd_schema.TRAININGS(merge_key, data_map) VALUES('t_10', '{ "trainingId": "t_10", "version": "1", "typeCode": "PRGM"}');
c.createStatement().execute("INSERT INTO bdd_schema.TRAININGS(merge_key, data_map) VALUES('t_11', '{ "trainingId": "t_11", "version": "1", "typeCode": "CRSE"}');
c.createStatement().execute("INSERT INTO bdd_schema.TRAININGS(merge_key, data_map) VALUES('t_12', '{ "trainingId": "t_12", "version": "1", "typeCode": "CRSE"}');
c.createStatement().execute("INSERT INTO bdd_schema.TRAININGS(merge_key, data_map) VALUES('t_13', '{ "trainingId": "t_13", "version": "1", "typeCode": "CRSE"}');
c.createStatement().execute("INSERT INTO bdd_schema.TRAININGS(merge_key, data_map) VALUES('t_20', '{ "trainingId": "t_20", "version": "1", "typeCode": "CRSE"}');
c.createStatement().execute("
c.createStatement().execute("--INSERT INTbdd_schema.O curriculums(merge_key, data_map) VALUES('t_10', '{ "trainingId": "t_10", "curriculumId": "t_10"}}');
c.createStatement().execute("INSERT INTO bdd_schema.CURRICULUMS(merge_key, data_map) VALUES('t_11', '{ "trainingId": "t_11", "curriculumId": "t_10"}');
c.createStatement().execute("INSERT INTO bdd_schema.CURRICULUMS(merge_key, data_map) VALUES('t_12', '{ "trainingId": "t_12", "curriculumId": "t_10"}');
c.createStatement().execute("INSERT INTO bdd_schema.CURRICULUMS(merge_key, data_map) VALUES('t_13', '{ "trainingId": "t_13", "curriculumId": "t_10"}');
c.createStatement().execute("INSERT INTO bdd_schema.CURRICULUMS(merge_key, data_map) VALUES('t_20', '{ "trainingId": "t_20", "curriculumId": "t_10"}');