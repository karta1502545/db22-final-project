# Final Poject

This final project tests whether you are capable to figure out what DBMS components may affect transaction latency.

In order to test this ability, you need to train a simple model and get better prediction accuracy.

## Steps

1. Fork this project
2. Trace the code of our latency estimator
3. Write a feature collector
4. Improve the model accuracy
5. Write a reportmabout what you have done in this project
7. Push your repository to Gitlab and open a merge request

## Constraints

Here are some constraints you must follow during coding
- You **can only** use either random forest or linear regression.
- Changing model parameters is allowed.
- You **can only** use the conservative locking protocal.

## Experiments

Base on the workload we provide, show the followings:
- Feature importance
- Improvement of MAE and MRE score
  - It’s better to use histograms to show your improvement.

You can also think about what kind of workload parameters are good for your work and do experiments on that setting.

### Notes

For each experiment, you need to run the same workload twice for collecting training and testing data.

[How to run the latency estimator?](latency-estimator/README.md)

## Report

- Briefly explain what you exactly do
  - How do you collect features
  - What features you collect and why
  - What is your model (structure & model type)
- Experiments
  - Your experiment environment including (a list of your hardware components, the operating system)
    - e.g. Intel Core i5-3470 CPU @ 3.2GHz, 16 GB RAM, 128 GB SSD, CentOS 7
  - Base on the workload we provide.
    - Feature importance
    - Improvement of MAE and MRE score
  - The benchmarks and the parameters that you choosed.
    - Explain your workload
    - Feature importance
    - Improvement of MAE and MRE score
  - Analyze and explain the result of the experiments
- **Discuss and conclude why these features importance**

Note: There is no strict limitation to the length of your report. Generally, a 2~3 pages report with some figures and tables is fine. **Remember to include all the group members' student IDs in your report.**

## Submission

The procedure of submission is as following:

1. Fork the final project on GitLab
2. Clone the repository you forked
3. Finish your work and write the report
4. Commit your work, push your work to GitLab.
    - Name your report as `[Team Number]_final_project_report.pdf`
        - E.g. team1_final_report_report.pdf
5. Open a merge request to the original repository.
    - Source branch: Your working branch.
    - Target branch: The branch with your team number. (e.g. `team-1`)
    - Title: `Team-X Submission` (e.g. `Team-1 Submission`).

Note: Each team only needs one submission.

**Important: We do not accept late submission.**

## No Plagiarism Will Be Tolerated

If we find you copy someone’s code, you will get 0 point for this assignment.


## Deadline

Submit your work before **2022/06/17 (Fri.) 23:59:59**.
