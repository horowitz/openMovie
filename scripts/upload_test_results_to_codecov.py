import subprocess

unitTestReports = str(subprocess.run(
    'find . -type f -name "jacocoUnitTestReport.xml" ',
    shell=True, 
    capture_output=True).stdout.decode('utf-8')).replace('./', '').split('\n')

print(f"🔎️ Found unit test reports {unitTestReports}")

instrumentationTestReports = str(subprocess.run(
    'find . -type f -name "report.xml" ',
    shell=True, 
    capture_output=True).stdout.decode('utf-8')).replace('./', '').split('\n')

print(f"🔎️ Found unit test reports {instrumentationTestReports}")


