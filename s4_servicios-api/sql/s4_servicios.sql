delete from ClienteEntity;
delete from FacturaEntity;

insert into FacturaEntity (id, duracion, preciomateriales, fecha, pagada, primerpago, solicitud_id, tarjetapago_id) values (10, 10, 10, '8/20/1996', 0, 0, 1, null);

delete from ServicioOfrecidoEntity;
insert into ServicioOfrecidoEntity (id,nombre, descripcion,precio,tipo)
            values(1,'Mantenimiento de lavadora','Se revisa y se realiza un mantenimiento preventivo a la labodora del cliente',123.2,'Plomeria');

insert into ServicioOfrecidoEntity (id,nombre, descripcion,precio,tipo)
            values(2,'Pintar habitación','Se cambia el color de una habitación según preferencias del cliente',23.08,'Pintura');

delete from TrabajadorEntity;

delete from CalificacionEntity;
delete from SolicitudServicioEntity;

insert into SolicitudServicioEntity (id, descripcion, estado, fechainicio, foto, calificacion_id, cliente_id, trabajador_id, factura_id) values (10,'Aseo casa', 'Desorden', '11/20/2019', 'foto.png', null, null, null, null);
insert into SolicitudServicioEntity (id, descripcion, estado, fechainicio, foto, calificacion_id, cliente_id, trabajador_id, factura_id) values (20,'Arreglo auto', 'No enciende', '12/20/2019', 'foto2.png', null, null, null, null);

insert into CalificacionEntity (id, comentario, puntaje, trabajador_id, solicitud_id) values (1,'Excelente', 5, null, 10);
insert into CalificacionEntity (id, comentario, puntaje, trabajador_id, solicitud_id) values (2,'Pesimo', 1, null, 20);


