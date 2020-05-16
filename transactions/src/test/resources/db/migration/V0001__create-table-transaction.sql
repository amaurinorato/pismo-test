CREATE TABLE transaction (
  transaction_id bigint(20) NOT NULL AUTO_INCREMENT,
  account_id bigint NOT NULL,
  operation_type_id bigint not null,
  amount DECIMAL not null,
  event_date timestamp not null)

