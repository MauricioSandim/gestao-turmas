function Button({ children, type = "button", onClick, className = "login-button", id }) {
  return (
    <button className={className} id={id} type={type} onClick={onClick}>
      {children}
    </button>
  );
}

export default Button;