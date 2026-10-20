
from extract import extract_data
from transform import transform_data
from load import load_to_s3


def run_pipeline():
    print("=" * 45)
    print("ZOMATO DATA PIPELINE STARTED")
    print("=" * 45)

    try:
        # Step 1: Extract data
        print("\nStep 1: Extracting data...")
        df = extract_data()

        # Step 2: Transform data
        print("\nStep 2: Cleaning data...")
        cleaned_df = transform_data(df)

        # Step 3: Load data to AWS S3
        print("\nStep 3: Uploading data to AWS S3...")
        load_to_s3(cleaned_df)

        print("\nPipeline completed successfully!")

    except Exception as error:
        print("\nPipeline failed!")
        print("Error:", error)
        raise


if __name__ == "__main__":
    run_pipeline()
