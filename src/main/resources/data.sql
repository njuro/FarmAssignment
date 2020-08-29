insert into farm (id, name, note)
values ('8e6e3b02-3673-4600-92d6-6465ae2bf816', 'Test Farm 1', 'Some note');

insert into farm (id, name, note)
values ('15a7d4e9-364e-48d5-8c6a-e471c4d68f38', 'Test Farm 2', NULL);

insert into farm (id, name, note)
values ('20e45791-d423-4d44-8018-a0060e085d35', 'Test Farm 3', 'This farm starts as empty');

insert into field (id, name, farm_id)
values ('fac7df88-0c00-4527-98ef-1a2732c9d872', 'Test Field 1', '8e6e3b02-3673-4600-92d6-6465ae2bf816');
insert into field (id, name, farm_id)
values ('b730f785-abcb-4129-a21b-112b5ad0c2c8', 'Test Field 2', '8e6e3b02-3673-4600-92d6-6465ae2bf816');
insert into field (id, name, farm_id)
values ('b513b7fa-13bb-464c-8e84-da4b6d28745c', 'Test Field 3', '15a7d4e9-364e-48d5-8c6a-e471c4d68f38');
