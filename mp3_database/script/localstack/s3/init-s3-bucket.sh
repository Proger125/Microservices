#!/bin/bash
# -- > Create S3 Bucket
echo $(awslocal s3 mb s3://mp3-bucket)