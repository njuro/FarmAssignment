insert into farm (id, name, note, country)
values ('8e6e3b02-3673-4600-92d6-6465ae2bf816', 'Sample Farm 1', 'Some note', 'CZE');

insert into farm (id, name, note, country)
values ('15a7d4e9-364e-48d5-8c6a-e471c4d68f38', 'Sample Farm 2', NULL, 'CZE');

insert into farm (id, name, note, country)
values ('20e45791-d423-4d44-8018-a0060e085d35', 'Sample Farm 3', 'This farm starts as empty', 'CZE');

insert into field (id, name, farm_id, geom)
values ('fac7df88-0c00-4527-98ef-1a2732c9d872', 'Sample Field 1', '8e6e3b02-3673-4600-92d6-6465ae2bf816',
        ST_GEOMFROMTEXT(
                'POLYGON((12.734248749316794 50.23775636829983,13.283565155566794 50.23775636829983,13.283565155566794 49.714927421557235,12.734248749316794 49.714927421557235,12.734248749316794 50.23775636829983))',
                4326));
insert into field (id, name, farm_id, geom)
values ('b730f785-abcb-4129-a21b-112b5ad0c2c8', 'Sample Field 2', '8e6e3b02-3673-4600-92d6-6465ae2bf816',
        ST_GEOMFROMTEXT(
                'POLYGON((14.360225311816794 50.040597222816366,15.063350311816794 50.040597222816366,15.063350311816794 49.487078019057755,14.360225311816794 49.487078019057755,14.360225311816794 50.040597222816366))',
                4326));
insert into field (id, name, farm_id, geom)
values ('b513b7fa-13bb-464c-8e84-da4b6d28745c', 'Sample Field 3', '15a7d4e9-364e-48d5-8c6a-e471c4d68f38',
        ST_GEOMFROMTEXT(
                'POLYGON((17.172725311816794 49.714927421557235,17.656123749316794 49.714927421557235,17.656123749316794 49.28683589927857,17.172725311816794 49.28683589927857,17.172725311816794 49.714927421557235))',
                4326));
