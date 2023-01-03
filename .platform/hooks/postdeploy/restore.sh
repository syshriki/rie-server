#!/bin/sh
cd /var/app/current
rm -f rieserver.db 
aws s3 cp s3://rie-data/db/latest.db rieserver.db