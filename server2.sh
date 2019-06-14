echo Starting Gunicorn.
exec gunicorn env_rest-devops.wsgi:application --bind 0.0.0.0:8083 --workers 3