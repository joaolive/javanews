#!/bin/bash

# Define the target directory where Spring Boot expects the keys
DEST_DIR="src/main/resources"

echo "🔐 Starting RSA key generation for the local environment..."

# Ensure the directory exists
mkdir -p $DEST_DIR

# 1. Generate a temporary private key
echo "⚙️  1/4: Generating temporary private key (2048 bits)..."
openssl genrsa -out $DEST_DIR/rsa_private_key_temp.pem 2048 2>/dev/null

# 2. Convert to PKCS#8 format (required by Java)
echo "🛡️  2/4: Converting to PKCS#8 format (private.pem)..."
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in $DEST_DIR/rsa_private_key_temp.pem -out $DEST_DIR/private.pem 2>/dev/null

# 3. Extract the public key
echo "🔑 3/4: Extracting public key (public.pem)..."
openssl rsa -in $DEST_DIR/private.pem -pubout -out $DEST_DIR/public.pem 2>/dev/null

# 4. Cleanup
echo "🧹 4/4: Cleaning up temporary files..."
rm $DEST_DIR/rsa_private_key_temp.pem

echo "========================================================"
echo "✅ SUCCESS! The keys have been generated in $DEST_DIR:"
echo "   - private.pem"
echo "   - public.pem"
echo "========================================================"
echo "🚀 You can now start your application!"