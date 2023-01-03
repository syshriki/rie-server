#!/bin/sh
BKDIR='/tmp/bk'
BUCKET='rie-data'
NOW=`date +%m-%d`
mkdir -p $BKDIR
cp /var/app/current/rieserver.db $BKDIR/$NOW.db
tar -czPf $BKDIR/$NOW-backup.tar.gz $BKDIR/$NOW.db
aws s3 cp $BKDIR/$NOW-backup.tar.gz s3://$BUCKET/db/$NOW-backup.tar.gz
aws s3 cp /var/app/current/rieserver.db s3://$BUCKET/db/latest.db
rm -r $BKDIR