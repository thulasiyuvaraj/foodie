
import boto3
import os
from io import BytesIO
from datetime import datetime


def load_to_s3(df):
    # Get bucket name from environment variable
    bucket_name = os.getenv("S3_BUCKET_NAME")

    if not bucket_name:
        raise ValueError(
            "Please set the S3_BUCKET_NAME environment variable."
        )

    # Create S3 client using configured AWS credentials
    s3 = boto3.client("s3")

    # Convert cleaned DataFrame into CSV bytes
    csv_buffer = BytesIO()
    df.to_csv(csv_buffer, index=False)
    csv_buffer.seek(0)

    # Create a unique output filename
    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
    s3_key = f"processed/zomato_cleaned_{timestamp}.csv"

    # Upload CSV to S3
    s3.put_object(
        Bucket=bucket_name,
        Key=s3_key,
        Body=csv_buffer.getvalue(),
        ContentType="text/csv"
    )

    print("Data uploaded successfully to AWS S3!")
    print(f"Bucket: {bucket_name}")
    print(f"File: {s3_key}")

    return s3_key


if __name__ == "__main__":
    print("Run pipeline.py to upload the cleaned dataset.")
