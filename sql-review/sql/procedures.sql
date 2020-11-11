CREATE OR REPLACE FUNCTION add_task(file_id character varying)
  RETURNS TABLE(id int)
  LANGUAGE plpgsql
 AS 
 $function$
 begin 
 return query insert into task (file_id) values (file_id) returning task.id;
 end;
 $function$;
 
 CREATE OR REPLACE FUNCTION add_task_log(
 	task_id int, 
 	message varchar(1000), 
 	state task_state)
  RETURNS TABLE (id bigint)
  LANGUAGE plpgsql
 AS 
 $function$
 begin
	 return query
	 	insert into tasklog (task_id, message, state) values (task_id, message, state) returning tasklog.id;
 end
 $function$;
 
CREATE OR REPLACE FUNCTION get_latest_log_entries()
  RETURNS TABLE
  	(task_id int, 
  	file_id varchar(1000), 
  	message varchar(1000), 
  	state task_state, 
  	ts timestamp with time zone)
  LANGUAGE plpgsql AS 
 $function$ 
 begin 
 	return query 
 	select distinct on (task.id) 
 		task.id, task.file_id, tasklog.message, tasklog.state, tasklog.timestamp 
 	from task inner join tasklog on task.id = tasklog.task_id 
 	order by task.id, timestamp desc; 
 end 
 $function$;
 
CREATE OR REPLACE FUNCTION get_latest_log_entry(our_task_id int)
  RETURNS TABLE
  	(task_id integer, 
  	file_id varchar(1000), 
  	message varchar(1000), 
  	state task_state, 
  	ts timestamptz)                                                                                                                                         +
  LANGUAGE plpgsql AS 
$function$ 
begin 
	return query 
	select distinct on (task.id) 
		task.id, task.file_id, tasklog.message, tasklog.state, tasklog.timestamp 
	from task inner join tasklog on task.id = tasklog.task_id 
	where task.id = our_task_id 
	order by task.id, timestamp desc; 
end 
$function$;