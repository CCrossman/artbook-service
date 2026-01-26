#!/bin/sh

echo "Starting Pre-Deploy Migrations..."
java -cp target/artbook-service.jar com.artbook.service.job.PreDeployJob

# Exit with success status (0) or Render will abort the deploy
exit 0