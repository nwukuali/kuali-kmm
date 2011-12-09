-- KR_COUNTRY_T
truncate table KR_COUNTRY_T;
insert INTO KR_COUNTRY_T select * from FINXX.Kr_Country_t;
select * from KR_COUNTRY_T;

-- KR_POSTAL_CODE_T
truncate table KR_POSTAL_CODE_T;
alter table KR_POSTAL_CODE_T
  disable constraint KR_POSTAL_CODE_TR2;
alter table KR_POSTAL_CODE_T
  disable constraint KR_POSTAL_CODE_TR3;
insert INTO KR_POSTAL_CODE_T select * from FINXX.KR_POSTAL_CODE_T;
select * from KR_POSTAL_CODE_T;

-- KR_STATE_T
truncate table KR_STATE_T;
insert INTO KR_STATE_T select * from FINXX.KR_STATE_T;
select * from KR_STATE_T;

-- KR_COUNTY_T
truncate table KR_COUNTY_T;
insert INTO KR_COUNTY_T select * from FINXX.KR_COUNTY_T;
select * from KR_COUNTY_T;

-- Re-enable constraints for KR_POSTAL_CODE_T
alter table KR_POSTAL_CODE_T
  enable constraint KR_POSTAL_CODE_TR2;
alter table KR_POSTAL_CODE_T
  enable constraint KR_POSTAL_CODE_TR3;

