function Input({ label, type = "text", placeholder, value, onChange, name, id, autoComplete, required, minLength, maxLength }) {
  return (
    <div className="Input">
      <input
        className="InputForm"
        type={type}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
        name={name}
        id={id}
        autoComplete={autoComplete}
        required={required}
        minLength={minLength}
        maxLength={maxLength}
      />
    </div>
  );
}

export default Input;