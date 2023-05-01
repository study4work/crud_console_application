Create table if not exists developer(
   developer_id int auto_increment not null,
   specialty_id int,
   firstName varchar(20) not null,
   lastName varchar(20) not null,
   status ENUM('ACTIVE' , 'DELETE'),
   primary key (developer_id),
   foreign key (specialty_id) references specialty(specialty_id)
);

create table if not exists skill(
   skill_id int auto_increment not null,
   skillDescription varchar(20) not null,
   status ENUM('ACTIVE' , 'DELETE'),
   primary key (skill_id)
);

create table if not exists specialty(
   specialty_id int auto_increment not null,
   descriptionSpecialty varchar(20),
   status ENUM('ACTIVE' , 'DELETE'),
   primary key (specialty_id)
);

create table if not exists developer_skill(
  developer_skill int auto_increment not null,
  deveoper_id int,
  skill_id int,
  primary key (developer_skill),
  foreign key (deveoper_id) references developer(developer_id),
  foreign key (skill_id) references skill(skill_id)
);