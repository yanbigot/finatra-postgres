with TRAINING_IDS as (select data_map->>'curriculumId' curriculumId, data_map->>'trainingId' trainingId from BDD_SCHEMA.CURRICULUMS)
select
    EMPLOYEES.data_map->>'ggi',
    FOLLOWUPS.data_map,
    TRAININGS.data_map,
    TRAINING_IDS.trainingId
from MANAGER_COLLABORATOR
    join BDD_SCHEMA.EMPLOYEES
        on MANAGER_COLLABORATOR.ggiCollaborator = EMPLOYEES.data_map->>'ggi'
    left join FOLLOWUPS
        on BDD_SCHEMA.EMPLOYEES.data_map->>'ggi' = FOLLOWUPS.data_map->>'ggi'
    left join BDD_SCHEMA.TRAININGS
        on FOLLOWUPS.data_map->>'trainingId' = TRAININGS.data_map->>'trainingId'
    left join TRAINING_IDS
        on FOLLOWUPS.data_map->>'trainingId' = TRAINING_IDS.curriculumId
order by EMPLOYEES.data_map->>'ggi' asc;
