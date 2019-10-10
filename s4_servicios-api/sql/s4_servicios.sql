delete from ClienteEntity;
delete from FacturaEntity;

insert into FacturaEntity (id, duracion, preciomateriales, fecha, pagada, primerpago, solicitud_id, tarjetapago_id) values (10, 10, 10, '8/20/1996', 0, 0, 1, null);
