export interface mortgage {
    id: number;
    principal: number;
    down_pmt: number;
    annual_rate: number;
    amort_years: number;
    loan_term: number;
    amort_schedule: string[][]
}