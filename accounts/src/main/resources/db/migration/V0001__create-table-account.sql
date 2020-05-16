CREATE TABLE account (
  account_id bigint(20) NOT NULL AUTO_INCREMENT,
  document_number bigint NOT NULL,
  PRIMARY KEY (account_id),
  UNIQUE(document_number)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

