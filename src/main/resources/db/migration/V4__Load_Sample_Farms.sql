insert into farm (id, name, note, country)
values ('8e6e3b02-3673-4600-92d6-6465ae2bf816', 'Sample Farm 1', 'Some note', 'CZE');

insert into farm (id, name, note, country)
values ('15a7d4e9-364e-48d5-8c6a-e471c4d68f38', 'Sample Farm 2', NULL, 'CZE');

insert into farm (id, name, note, country)
values ('20e45791-d423-4d44-8018-a0060e085d35', 'Sample Farm 3', 'This farm starts as empty', 'CZE');

insert into field (id, name, farm_id, geom)
values ('fac7df88-0c00-4527-98ef-1a2732c9d872', 'Sample Field 1', '8e6e3b02-3673-4600-92d6-6465ae2bf816',
        ST_GEOMFROMTEXT(
                'POLYGON((12.618701847351126 50.21297750077345,13.376758487976126 50.21297750077345,13.376758487976126 49.689877890378305,12.618701847351126 49.689877890378305,12.618701847351126 50.21297750077345))',
                4326));
insert into field (id, name, farm_id, geom)
values ('b730f785-abcb-4129-a21b-112b5ad0c2c8', 'Sample Field 2', '8e6e3b02-3673-4600-92d6-6465ae2bf816',
        ST_GEOMFROMTEXT(
                'POLYGON((12.563770206726126 49.3976036246191,13.233936222351126 49.3976036246191,13.233936222351126 48.908997152997344,12.563770206726126 48.908997152997344,12.563770206726126 49.3976036246191))',
                4326));
insert into field (id, name, farm_id, geom)
values ('b513b7fa-13bb-464c-8e84-da4b6d28745c', 'Sample Field 3', '15a7d4e9-364e-48d5-8c6a-e471c4d68f38',
        ST_GEOMFROMTEXT(
                'POLYGON((12.772510441101126 48.59026957020422,13.640430362976126 48.59026957020422,13.640430362976126 48.10838387531738,12.772510441101126 48.10838387531738,12.772510441101126 48.59026957020422))',
                4326));
