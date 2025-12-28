import eslint from '@eslint/js';
import tseslint from 'typescript-eslint';

export default tseslint.config(
  eslint.configs.recommended,
  ...tseslint.configs.recommended,
  {
    rules: {
      "no-console": "warn",
      "complexity": ["error", 10], // Detecta Code Smells (OKR 3)
      "@typescript-eslint/no-unused-vars": "error" // Reduce Deuda Técnica (OKR 3)
    }
  }
);
