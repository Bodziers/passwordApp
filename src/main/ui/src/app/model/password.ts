
export interface IPassword {
  id?: number;
  password?: string;
}

export class Password implements IPassword {

  constructor(
    public id?: number,
    public password?: string
  ) {}
}

export function getPasswordId (password: Password): number | undefined {
  return password.id;
}
