import aws from 'aws-sdk'

const region = "us-east-2"
const bucketName = "connexionphotos"
const accessKeyId = ""
const secretAccessKey = ""

const s3 = new aws.S3({
    region,
    accessKeyId,
    secretAccessKey,
    signatureVersion: '4'
})